package com.fanxb.backservice.dao;

import com.fanxb.backservice.entity.AttentionPrice;
import com.fanxb.backservice.entity.AttentionPriceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 *
 * @author fanxb
 * @date 2019/3/28 16:15
 */
@Component
public interface AttentionPriceDao {

    /**
     * Description: 根据游戏id获取所有关注了这个游戏的用户信息
     *
     * @author fanxb
     * @date 2019/4/12 11:57
     * @param gameId gameId
     * @return java.util.List<com.fanxb.backservice.entity.AttentionPrice>
     */
    List<AttentionPriceInfo> getByGameId(int gameId);

    /**
     * Description:
     *
     * @author fanxb
     * @date 2019/4/12 11:59
     * @param price 价格
     * @return void
     */
    void addOne(AttentionPrice price);

    /**
     * Description:
     *
     * @author fanxb
     * @date 2019/4/12 16:17
     * @param gameId gameId
     * @param userId userId
     * @return int
     */
    AttentionPrice isWatch(@Param("gameId") int gameId,@Param("userId") int userId);

    /**
     * Description:
     *
     * @author fanxb
     * @date 2019/5/8 16:20
     * @param gameId gameId
     * @param userId userId
     */
    void deleteOne(@Param("gameId") int gameId,@Param("userId") int userId);

    /**
     * Description: 删除过期数据
     *
     * @author fanxb
     * @date 2019/5/8 16:19
     * @param str idStr
     */
    void deleteByGameIdStr(@Param("str") String str);

}