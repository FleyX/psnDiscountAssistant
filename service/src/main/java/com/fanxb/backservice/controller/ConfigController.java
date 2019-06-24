package com.fanxb.backservice.controller;

import com.fanxb.backservice.entity.output.Result;
import com.fanxb.backservice.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 * Created By Fxb
 * Date: 2019/6/8
 * Time: 12:44
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("")
    public Result getAll() {
        return Result.success(configService.getAll());
    }
}
