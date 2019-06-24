package com.fanxb.test.util;

import com.fanxb.backservice.BackServiceApplication;
import com.fanxb.backservice.entity.MailInfo;
import com.fanxb.backservice.util.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA
 * Created By Fxb
 * Date: 2019/4/7
 * Time: 19:07
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackServiceApplication.class)
public class MailUtilTest {

    @Test
    public void sendTextMailTest(){
        MailInfo info = new MailInfo();
        info.setReceiver("2728474645@qq.com");
        info.setSubject("工作报告");
        info.setContent("本周工作任务全部完成，请指示");
        MailUtil.sendTextMail(info);
    }
}
