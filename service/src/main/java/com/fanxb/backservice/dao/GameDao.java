package com.fanxb.backservice.dao;

import com.fanxb.backservice.entity.Game;
import com.fanxb.backservice.entity.input.GameListParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/3/21 15:58
 */
@Component
public interface GameDao {

    /**
     * Description:
     *
     * @param serverId 服务id
     * @param platform 平台id
     * @param isCut    是否是折扣游戏
     * @return int
     * @author fanxb
     * @date 2019/3/21 16:08
     */
    int getGameCount(@Param("serverId") int serverId, @Param("platform") String platform, @Param("type") String type, @Param("language") String language
            , @Param("isCut") int isCut);

    /**
     * Description: 查询关注游戏数
     *
     * @param userId   用户id
     * @param serverId 服务器id
     * @param platform 平台
     * @param language 语言
     * @return int
     * @author fanxb
     * @date 2019/5/10 17:23
     */
    int getAttentionGameCount(@Param("userId") int userId, @Param("serverId") int serverId, @Param("platform") String platform
            , @Param("language") String language);

    /**
     * Description: 条件查询游戏列表
     *
     * @param param 参数
     * @return java.util.List<com.fanxb.backservice.entity.Game>
     * @author fanxb
     * @date 2019/3/21 17:24
     */
    List<Game> getGameList(GameListParam param);


    /**
     * Description: 获取我的关注列表
     *
     * @param userId userId
     * @param param  参数
     * @return java.util.List<com.fanxb.backservice.entity.Game>
     * @author fanxb
     * @date 2019/4/18 15:54
     */
    List<Game> getAttentionGameList(@Param("userId") int userId, @Param("param") GameListParam param);

    /**
     * Description: 获取游戏详情
     *
     * @param id gameId
     * @return com.fanxb.backservice.entity.Game
     * @author fanxb
     * @date 2019/3/27 19:47
     */
    Game getGameDetail(int id);

    /**
     * Description: 全文检索搜索
     *
     * @param serverId  serverId
     * @param platform  平台
     * @param language  语言
     * @param searchStr 关键词
     * @return java.util.List<com.fanxb.backservice.entity.Game>
     * @author fanxb
     * @date 2019/4/18 15:36
     */
    List<Game> fullTextSearch(@Param("serverId") int serverId, @Param("platform") String platform
            , @Param("language") String language, @Param("searchStr") String searchStr);

    /**
     * Description: 获取某个游戏相关的游戏
     *
     * @param storeId storeId
     * @return java.util.List<com.fanxb.backservice.entity.Game>
     * @author fanxb
     * @date 2019/5/7 16:33
     */
    List<Game> getChildren(String storeId);

    /**
     * Description: 设置三天未更新的valid为0
     *
     * @param time time
     * @author fanxb
     * @date 2019/5/8 15:56
     */
    void invalidGame(long time);

    /**
     * Description: 删除数据
     *
     * @param str str
     * @author fanxb
     * @date 2019/5/8 16:16
     */
    void deleteByIdStr(@Param("str") String str);
}
