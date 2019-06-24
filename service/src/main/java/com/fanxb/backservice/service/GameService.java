package com.fanxb.backservice.service;

import com.fanxb.backservice.dao.*;
import com.fanxb.backservice.entity.AttentionPrice;
import com.fanxb.backservice.entity.CutMessage;
import com.fanxb.backservice.entity.Game;
import com.fanxb.backservice.entity.PriceHistory;
import com.fanxb.backservice.entity.input.GameListParam;
import com.fanxb.backservice.exception.CustomException;
import com.fanxb.backservice.util.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/3/21 15:58
 */
@Service
@Slf4j
public class GameService {

    private static Set<String> orderKeySet = new HashSet<>();

    static {
        orderKeySet.add("name");
        orderKeySet.add("publishTime");
        orderKeySet.add("currentPrice");
        orderKeySet.add("cutPercent");
        orderKeySet.add("psnScore");
        orderKeySet.add("psnScoreNum");
    }

    @Autowired
    private GameDao gameDao;
    @Autowired
    private PriceHistoryDao historyDao;
    @Autowired
    private ServerDao serverDao;
    @Autowired
    private AttentionPriceDao attentionPriceDao;
    @Autowired
    private CutMessageDao cutMessageDao;
    @Autowired
    private LanguageDao languageDao;


    /**
     * Description:获取游戏数目
     *
     * @return int
     * @author fanxb
     * @date 2019/4/18 15:33
     */
    public int getCount(int serverId, String platform, String type, String language, int isCut) {
        return this.gameDao.getGameCount(serverId, platform, type, language, isCut);
    }

    public int getAttentionCount(int serverId, String platform, String language) {
        int userId = UserContextHolder.get().getUser().getUserId();
        return this.gameDao.getAttentionGameCount(userId, serverId, platform, language);
    }

    /**
     * Description: 获取游戏列表
     *
     * @param param 参数
     * @return java.util.List<com.fanxb.backservice.entity.Game>
     * @author fanxb
     * @date 2019/4/18 15:13
     */
    public List<Game> getList(GameListParam param) {
        if (!orderKeySet.contains(param.getOrderKey())) {
            throw new CustomException("排序参数不符合要求：" + param.getOrderKey());
        }
        List<Game> res = this.gameDao.getGameList(param);
        res.forEach(item -> item.setDescription(""));
        return res;
    }

    /**
     * Description: 获取关注游戏列表
     *
     * @param param param
     * @return java.util.List<com.fanxb.backservice.entity.Game>
     * @author fanxb
     * @date 2019/4/18 16:06
     */
    public List<Game> getAttentionList(GameListParam param) {
        if (!orderKeySet.contains(param.getOrderKey())) {
            throw new CustomException("排序参数不符合要求：" + param.getOrderKey());
        }
        int userId = UserContextHolder.get().getUser().getUserId();
        List<Game> res = this.gameDao.getAttentionGameList(userId, param);
        res.forEach(item -> item.setDescription(""));
        return res;
    }

    /**
     * Description: 获取游戏详情
     *
     * @param id gameId
     * @author fanxb
     * @date 2019/4/11 16:41
     */
    public Map<String, Object> getGameDetail(int id) {
        Map<String, Object> map = new HashMap<>(2);
        Game game = this.gameDao.getGameDetail(id);
        map.put("detail", game);
        map.put("history", this.historyDao.getPriceHistory(id));
        map.put("children", this.gameDao.getChildren(game.getStoreId()));
        //判断是否已经关注此游戏
        AttentionPrice attentionPrice = attentionPriceDao.isWatch(id, UserContextHolder.get().getUser().getUserId());
        map.put("attention", attentionPrice);
        return map;
    }


    /**
     * Description: 全文检索
     *
     * @return java.util.List<com.fanxb.backservice.entity.Game>
     * @author fanxb
     * @date 2019/4/18 15:33
     */
    public List<Game> fullTextSearch(int serverId, String platform, String language, String searchStr) {
        List<Game> games = this.gameDao.fullTextSearch(serverId, platform, language, searchStr);
        games.forEach(item -> item.setDescription(""));
        return games;
    }


    /**
     * Description:将超过三天未更新的game设置为无效
     *
     * @author fanxb
     * @date 2019/5/8 15:50
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void invalidGame() {
        gameDao.invalidGame(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000);
    }
}
