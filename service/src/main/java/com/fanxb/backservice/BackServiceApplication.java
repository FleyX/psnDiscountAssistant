package com.fanxb.backservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description:
 *
 * @author fanxb
 * @date 2019/3/28 15:28
 */
@SpringBootApplication
@MapperScan("com.fanxb.backservice.dao")
@EnableScheduling
@EnableTransactionManagement
public class BackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackServiceApplication.class, args);
    }

}
