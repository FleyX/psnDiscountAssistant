package com.fanxb.backservice.dao;

import com.fanxb.backservice.entity.CutMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/3/28 15:15
 */
@Component
public interface CutMessageDao {

    /**
     * Description: 更新一条数据
     *
     * @author fanxb
     * @date 2019/3/28 15:17
     * @param id id
     * @param overTime overTime
     * @return void
     */
    void setOver(@Param("id") int id,@Param("overTime") long overTime);

    /**
     * Description:
     *
     * @author fanxb
     * @date 2019/5/7 11:11
     * @param time 此时间戳之前的已经处理完毕
     * @param overTime 处理完毕时间
     * @return void
     */
    void setOverByCreateTime(@Param("time")long time, @Param("overTime") long overTime);

    /**
     * Description: 获取所有未处理的
     *
     * @param time 处理此时间戳之前的数据
     * @author fanxb
     * @date 2019/3/28 15:17
     * @return java.util.List<com.fanxb.backservice.entity.CutMessage>
     */
    List<CutMessage> getUnOverList(long time);

    /**
     * Description: 删除过期数据
     *
     * @author fanxb
     * @date 2019/5/8 16:19
     * @param str idStr
     */
    void deleteByGameIdStr(@Param("str") String str);
}
