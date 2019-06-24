package com.fanxb.backservice.dao;

import com.fanxb.backservice.entity.FormKey;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/5/6 16:23
 */
@Component
public interface FormKeyDao {

    /**
     * Description: 批量插入
     *
     * @param list list
     * @author fanxb
     * @date 2019/5/6 17:49
     */
    void insertMany(List<FormKey> list);

    /**
     * Description: 根据用户id获取列表
     *
     * @param userId userId
     * @return java.util.List<java.lang.String>
     * @author fanxb
     * @date 2019/5/6 17:49
     */
    List<FormKey> getByUserId(int userId);

    /**
     * Description: 根据id删除
     *
     * @param formKeyId id
     * @return void
     * @author fanxb
     * @date 2019/5/7 13:58
     */
    void deleteByFormKeyId(int formKeyId);

    /**
     * Description: 清理过期的formId
    *
     * @author fanxb
     * @date 2019/5/7 14:07
     * @param time 当前时间戳
     * @return void
     */
    void clearExpire(long time);
}
