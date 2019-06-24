package com.fanxb.backservice.dao;

import com.fanxb.backservice.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author fanxb
 * @date 2019/3/28 16:15
 */
@Component
public interface UserDao {

    /**
     * Description: 根据用户id获取用户西悉尼
     *
     * @param userId uId
     * @return com.fanxb.backservice.entity.User
     * @author fanxb
     * @date 2019/4/16 14:27
     */
    User getByUserId(int userId);

    /**
     * 根据微信openId获取用户信息
     *
     * @param openId openId
     * @return
     */
    User getByOpenId(String openId);

    /**
     * Description: 新增用户
     *
     * @param user user
     * @author fanxb
     * @date 2019/4/16 14:28
     */
    void addOne(User user);

    /**
     * Description: 修改联系方式
     *
     * @param userId userId
     * @param column 键
     * @param value  值
     * @return void
     * @author fanxb
     * @date 2019/4/16 14:42
     */
    void updateContactInfo(@Param("userId") int userId, @Param("column") String column, @Param("value") String value);

    /**
     * Description:
     *
     * @param userId    用户id
     * @param timestamp 当前时间戳
     * @author fanxb
     * @date 2019/4/30 9:08
     */
    void updateLastLoginTime(@Param("userId") int userId, @Param("timestamp") long timestamp);


}