package com.fanxb.backservice.dao;

import com.fanxb.backservice.entity.Server;
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
public interface ServerDao {

    /**
     * Description: 条件查询游戏列表
     *
     * @return java.util.List<com.fanxb.backservice.entity.Game>
     * @author fanxb
     * @date 2019/3/21 17:24
     */
    List<Server> getServerList();

    Server getServerDetail(int id);

}
