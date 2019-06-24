package com.fanxb.backservice.controller;

import com.fanxb.backservice.entity.output.Result;
import com.fanxb.backservice.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 * Created By Fxb
 * Date: 2019/3/24
 * Time: 12:03
 */
@RestController
@RequestMapping("/server")
public class ServerController {

    private ServerService serverService;

    @Autowired
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/list")
    public Result getServerList() {
        return Result.success(this.serverService.getList());
    }
}
