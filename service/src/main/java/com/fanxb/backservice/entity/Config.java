package com.fanxb.backservice.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Created By Fxb
 * Date: 2019/6/8
 * Time: 12:46
 */
@Data
public class Config {
    private int configId;
    private String key;
    private String value;
    private String type;
}
