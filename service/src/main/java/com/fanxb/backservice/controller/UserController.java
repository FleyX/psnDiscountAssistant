package com.fanxb.backservice.controller;

import com.fanxb.backservice.entity.input.ChangeContactInfo;
import com.fanxb.backservice.entity.output.Result;
import com.fanxb.backservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/4/4 15:52
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public Result login(String code) {
        Map<String, Object> res = this.userService.login(code);
        return Result.success(res);
    }

    @GetMapping("sendVerifyCode")
    public Result sendVerifyCode(String type, String value) {
        this.userService.sendVerifyCode(type, value);
        return Result.success(null);
    }

    @PostMapping("updateContactInfo")
    public Result updateContactInfo(@RequestBody ChangeContactInfo info) {
        this.userService.updateContactInfo(info);
        return Result.success(null);
    }
}
