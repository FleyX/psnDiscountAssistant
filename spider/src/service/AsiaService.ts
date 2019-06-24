/**
 * 先从港服入手
 */
import StringHelper from "../util/StringHelper";
import GameEntity from "../entity/GameEntity";
import moment from "moment";
import ServerEntity from "../entity/ServerEntity";
import * as axios from "axios";
import ListApi from "../entity/ListApi";
import GameDao from "../dao/GameDao";
import PriceHisotryDao from "../dao/PriceHistoryDao";
import LanguageDao from "../dao/LanguageDao";
import TimeUtil from "../util/TimeUtil";
import CutMessage from "../entity/CutMessage";
import CutMessageDao from "../dao/CutMessageDao";
import config from "../config";
import { MysqlUtil, Res, mysql } from '../util/MysqlUtil'

interface item {
  id: string;
  type: string;
}

class AsiaService {
  /**
   * 服务信息
   */
  server: ServerEntity;
  /**
   * 当前index
   */
  currentIndex: number;
  /**
   * 游戏总数
   */
  totalNum: number;
  listApi: ListApi;
  constructor(server: ServerEntity) {
    this.server = server;
    this.server.listApi = JSON.parse(this.server.listApi.toString());
    this.currentIndex = 0;
    this.totalNum = -1;
  }

  public async start(): Promise<void> {
    console.log(`---------------开始处理${this.server.name}游戏：${new Date().toLocaleString()}`);
    for (let i = 0; i < this.server.listApi.length; i++) {
      this.listApi = this.server.listApi[i];
      for (; this.currentIndex < this.totalNum || this.totalNum == -1;) {
        let item, list;
        try {
          list = await this.resolvListPage();
        } catch (error) {
          console.error(`获取列表出现故障:${error.message}\n${error.stack}`);
          continue;
        }
        for (let index in list) {
          item = list[index];
          let connection = await MysqlUtil.pool.getConnection();
          try {
            await connection.beginTransaction();
            await this.resolvGameDetail(connection, item);
            await connection.commit();
          } catch (error) {
            await connection.rollback();
            console.error(`处理:${this.server.name},${this.listApi.platform},${item.id},过程中出现异常:${error.message}\n${error.stack},${error.sql}`);
          } finally {
            connection.release();
          }
        }
      }
      console.log(`${this.listApi.platform}:处理完毕：${new Date().toLocaleString()}`);
      this.currentIndex = 0;
      this.totalNum = -1;
    }
    console.log(`本服处理完毕:${this.server.name}`);
  }

  /**
   * 解析列表页
   */
  private async resolvListPage(): Promise<Array<item>> {
    let url = this.listApi.api.replace("****", this.currentIndex.toString());
    let res = (await axios.default.get(url)).data.data;
    if (this.totalNum == -1) {
      this.totalNum = res["attributes"]["total-results"];
    }
    let list = res["relationships"]["children"]["data"];
    this.currentIndex += list.length;
    return list;
  }

  /**
   * 解析游戏详情页
   * @param url 地址
   */
  private async resolvGameDetail(connection: mysql.PoolConnection, item: item): Promise<void> {
    if (config.env == 'dev') {
      // item.id = "UP0006-CUSA02429_00-ASIA000000000039";
    }
    let old: GameEntity = await GameDao.getOneByIdAndPlatform(item.id, this.listApi.platform, this.server.serverId);
    //等待一个随机的时间,避免对服务器压力过大
    await TimeUtil.sleep(StringHelper.getRandomNumber(1000, 2000));
    let res = await axios.default.get(this.server.gameDetailApi.replace("****", item.id));
    let gameDetail = res.data["included"][0]["attributes"];
    let gameInfo: GameEntity = new GameEntity();
    if (old != null) {
      gameInfo.id = old.id;
      gameInfo.createTime = old.createTime;
      gameInfo.zhName = old.zhName;
    } else {
      gameInfo.createTime = Date.now();
    }
    gameInfo.updateTime = Date.now();
    gameInfo.serverId = this.server.serverId;
    gameInfo.storeId = item.id;
    gameInfo.name = gameDetail.name;
    gameInfo.platform = this.listApi.platform;
    if (gameDetail.parent != null) {
      gameInfo.parentStoreId = gameDetail.parent.id;
    }
    let dateStr = gameDetail["release-date"];
    if (!StringHelper.isEmpty(dateStr)) {
      gameInfo.publishTime = moment(dateStr).unix() * 1000;
    }
    //处理游戏价格
    await this.detailPrice(gameDetail, gameInfo, old);
    if (gameDetail.genres.length == 0) {
      console.log(`此游戏无游戏类别，跳过：:${this.server.name},${this.listApi.platform},${gameInfo.storeId}\n`);
      return;
    }
    gameInfo.type = gameDetail.genres[0];
    if (gameInfo.type)
      //获取游戏icon
      gameInfo.icon = gameDetail["thumbnail-url-base"];
    //获取商店评分
    gameInfo.psnScore = gameDetail["star-rating"].score == null ? 0 : gameDetail["star-rating"].score * 100;
    gameInfo.psnScoreNum = gameInfo.psnScore == 0 ? 0 : gameDetail["star-rating"].total;
    //获取描述信息
    gameInfo.description = gameDetail["long-description"];

    if (old != null) {
      // 清理旧数据
      await GameDao.deleteOldData(old.id, connection);
    }
    let gameId = await GameDao.insertOne(gameInfo, connection);
    //插入支持的语言列表
    let langArr = gameInfo.language.split(",").filter(item => item.trim().length > 0);
    for (let i = 0; i < langArr.length; i++) {
      await LanguageDao.insertOne(gameId, langArr[i], connection);
    }
    //插入当前价格,只记录价格变化的点
    if (old == null || old.currentPrice != gameInfo.currentPrice || old.plusPrice != gameInfo.plusPrice) {
      await PriceHisotryDao.insertOne(TimeUtil.getZeroTime().getTime(), gameId, gameInfo.currentPrice
        , gameInfo.plusPrice, connection);
    }
    //判断是否有打折
    if (old != null && gameInfo.currentPrice != old.currentPrice && gameInfo.cutPercent > 0) {
      let message = new CutMessage();
      message.gameId = gameId;
      message.createTime = Date.now();
      await CutMessageDao.inserOne(message, connection);
    }
    console.log(`处理完毕：${gameInfo.name},${gameInfo.storeId},${new Date().toLocaleString()}`);
  }

  /**
   * 解析游戏价格，支持语言等
   * @param gameDetail 源数据
   * @param gameInfo 解析后的数据
   * @param old  上一次解析的数据
   */
  private async detailPrice(gameDetail: any, gameInfo: GameEntity, old: GameEntity) {
    let skuId = gameDetail["default-sku-id"];
    if (!StringHelper.isEmpty(skuId)) {
      let sku = (gameDetail["skus"] as Array<any>).find(item => item.id == skuId);
      //处理语言
      let language: string = sku.name ? sku.name : '';
      gameInfo.language = language
        .replace("简体", "")
        .replace("繁体", "")
        .replace("欧州", "")
        .replace("文版", "")
        .split("")
        .join(",");
      //处理价格
      gameInfo.currentPrice = sku.prices["non-plus-user"]["actual-price"].value;
      gameInfo.cutPercent = sku.prices["non-plus-user"]["discount-percentage"];
      gameInfo.rmbPrice = Math.round(gameInfo.currentPrice * this.server.exchangeRate);
      gameInfo.plusPrice = sku.prices["plus-user"]["actual-price"].value;
      gameInfo.plusCutPercent = sku.prices["plus-user"]["discount-percentage"];
      gameInfo.rmbPlusPrice = Math.round(gameInfo.plusPrice * this.server.exchangeRate);
      gameInfo.isPlus = sku.prices["plus-user"]["is-plus"] ? 1 : 0;
      //获取原价
      let originPrice = sku.prices["non-plus-user"]["strikethrough-price"];
      if (originPrice == null) {
        gameInfo.originPrice = gameInfo.currentPrice;
      } else {
        gameInfo.originPrice = originPrice.value;
      }
      //获取打折结束时间
      let overTimeStr = sku.prices["non-plus-user"]["availability"]["end-date"];
      if (!StringHelper.isEmpty(overTimeStr)) {
        gameInfo.cutOverTime = moment(overTimeStr).unix() * 1000;
      }
      //判断是否史低
      if (old != null) {
        if (gameInfo.currentPrice == old.currentPrice) {
          gameInfo.isLowest = old.isLowest;
        } else {
          let lowestPrice = await PriceHisotryDao.getLowestPrice(old.id);
          gameInfo.isLowest = lowestPrice > gameInfo.currentPrice ? 1 : lowestPrice == gameInfo.currentPrice ? 2 : 0;
        }
      } else {
        gameInfo.isLowest = 0;
      }
    }
  }

}

export default AsiaService;
