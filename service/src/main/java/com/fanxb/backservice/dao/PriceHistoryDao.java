package com.fanxb.backservice.dao;

import com.fanxb.backservice.entity.PriceHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By Fxb
 * Date: 2019/3/25
 * Time: 20:20
 */
@Component
public interface PriceHistoryDao {

    /**
     * @param gameId gameId
     * @return
     */
    List<PriceHistory> getPriceHistory(int gameId);

    /**
     * Description: 删除过期数据
     *
     * @author fanxb
     * @date 2019/5/8 16:18
     * @param str idStr
     * @return void
     */
    void deleteByGameIdStr(@Param("str") String str);

}
