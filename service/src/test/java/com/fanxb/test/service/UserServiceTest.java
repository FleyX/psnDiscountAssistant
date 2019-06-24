package com.fanxb.test.service;

import com.fanxb.backservice.BackServiceApplication;
import com.fanxb.backservice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA
 * Created By Fxb
 * Date: 2019/4/8
 * Time: 21:09
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackServiceApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void loginTest(){
        userService.login("033dD0660qqO8E1MiU260rpU560dD06M");
    }
}
