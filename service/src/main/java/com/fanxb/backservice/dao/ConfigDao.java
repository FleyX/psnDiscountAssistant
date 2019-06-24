package com.fanxb.backservice.dao;

import com.fanxb.backservice.entity.Config;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By Fxb
 * Date: 2019/6/8
 * Time: 12:47
 */
@Component
public interface ConfigDao {

    /**
     * 获取所有配置
     * @return
     */
    List<Config> getAll();
}
