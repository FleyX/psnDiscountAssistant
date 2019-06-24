package com.fanxb.backservice.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/5/8 16:44
 */
@Component
public interface LanguageDao {

    void deleteByGameIdStr(@Param("str") String str);
}
