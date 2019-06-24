package com.fanxb.backservice.entity;

import lombok.Data;

/**
 * Description:关注游戏
 *
 * @author fanxb
 * @date 2019/5/6 17:10
 */
@Data
public class AttentionPrice {
    private Integer gameId;
    private Integer userId;
    private String type;
    private Integer value;
    private Long createTime;
}
