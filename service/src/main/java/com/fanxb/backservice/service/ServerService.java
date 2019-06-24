package com.fanxb.backservice.service;

import com.fanxb.backservice.dao.GameDao;
import com.fanxb.backservice.dao.ServerDao;
import com.fanxb.backservice.entity.Game;
import com.fanxb.backservice.entity.Server;
import com.fanxb.backservice.entity.input.GameListParam;
import com.fanxb.backservice.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/3/21 15:58
 */
@Service
public class ServerService {

    private ServerDao serverDao;

    @Autowired
    public ServerService(ServerDao serverDao) {
        this.serverDao = serverDao;
    }


    public List<Server> getList() {
        return this.serverDao.getServerList();
    }
}
