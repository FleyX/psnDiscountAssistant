import GameEntity from '../entity/GameEntity';
import TimeUtil from '../util/TimeUtil';
import { MysqlUtil, Res, mysql } from '../util/MysqlUtil'

class GameDao {
  /**
   * 获取全部地区服
   */

  /**
   *
   * @param id storeId
   * @param platform  平台
   */
  static async getOneByIdAndPlatform(id: string, platform: string, serverId: number): Promise<GameEntity> {
    let res = await MysqlUtil.getRow('select * from game where storeId=? and platform=? and serverId=?', [id, platform, serverId]);
    return res as GameEntity;
  }

  /**
   * 清除与此游戏相关的数据
   * @param id game id
   */
  static async deleteOldData(id: number, conn: mysql.PoolConnection): Promise<void> {
    let sql = `delete from game where id=?;delete from language where gameId=?;`;
    await MysqlUtil.execute(sql, [id, id], conn);
  }

  /**
   * 插入一条游戏详情
   * @param game gameEntity
   * @returns id
   */
  static async insertOne(game: GameEntity, conn: mysql.PoolConnection): Promise<number> {
    let sql = `insert into game(${game.id == -1 ? '' : 'id,'}serverId,storeId,name,zhName,platform,icon
      ,description,language,publishTime,type,currentPrice,rmbPrice,cutPercent,plusPrice,rmbPlusPrice
      ,plusCutPercent,isPlus,originPrice,
      cutOverTime,isLowest,psnScore,psnScoreNum,mediaScore,createTime,updateTime,parentStoreId) 
      values(${game.id == -1 ? '' : game.id + ','}?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)`;
    let res = await MysqlUtil.execute(
      sql, [game.serverId, game.storeId, game.name, game.zhName, game.platform, game.icon, game.description, game.language,
      game.publishTime, game.type, game.currentPrice, game.rmbPrice, game.cutPercent, game.plusPrice, game.rmbPlusPrice,
      game.plusCutPercent, game.isPlus, game.originPrice, game.cutOverTime, game.isLowest, game.psnScore, game.psnScoreNum,
      game.mediaScore, game.createTime, game.updateTime, game.parentStoreId], conn);
    if (game.id == -1) {
      return res.rows.insertId;
    } else {
      return game.id;
    }
  }
}

export default GameDao;
