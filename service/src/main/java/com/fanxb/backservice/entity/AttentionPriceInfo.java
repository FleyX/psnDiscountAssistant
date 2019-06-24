package com.fanxb.backservice.entity;

import lombok.Data;

/**
 * 类功能简述：查出折扣关注的所有详细信息
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/5/7 10:01
 */
@Data
public class AttentionPriceInfo {
    private Integer gameId;
    private Integer serverId;
    private String name;
    private Integer rmbPrice;
    private Integer currentPrice;
    private Integer cutPercent;
    private String type;
    private Integer value;
    private Integer userId;
    private String openId;
    private String email;
    private Integer isLowest;
}
