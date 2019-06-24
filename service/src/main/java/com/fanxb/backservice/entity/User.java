package com.fanxb.backservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * user
 *
 * @author
 */
@Data
public class User implements Serializable {
    /**
     * id
     */
    private Integer userId;

    /**
     * 微信id
     */
    private String openId;


    private String email;

    /**
     * 手机号
     */
    private String phone;

    private static final long serialVersionUID = 1L;

}