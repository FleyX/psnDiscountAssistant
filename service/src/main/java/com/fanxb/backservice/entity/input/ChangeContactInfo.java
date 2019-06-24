package com.fanxb.backservice.entity.input;

import lombok.Data;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/4/23 15:56
 */
@Data
public class ChangeContactInfo {
    private String type;
    private String value;
    private String verifyCode;
}
