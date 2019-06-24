package com.fanxb.backservice.entity;

import lombok.Data;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/5/6 16:22
 */
@Data
public class FormKey {
    private int formKeyId;
    private int userId;
    private String value;
    private long expireTime;
}
