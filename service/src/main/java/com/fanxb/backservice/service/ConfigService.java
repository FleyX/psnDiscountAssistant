package com.fanxb.backservice.service;

import com.fanxb.backservice.dao.ConfigDao;
import com.fanxb.backservice.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By Fxb
 * Date: 2019/6/8
 * Time: 12:45
 */
@Service
public class ConfigService {

    @Autowired
    private ConfigDao configDao;

    public Map<String, Object> getAll() {
        List<Config> list = configDao.getAll();
        Map<String, Object> res = new HashMap<>(list.size());
        for (Config config : list) {
            Object obj;
            switch (config.getType()) {
                case "int":
                    obj = Integer.valueOf(config.getValue());
                    break;
                default:
                    obj = config.getValue();
            }
            res.put(config.getKey(), obj);
        }
        return res;
    }

}
