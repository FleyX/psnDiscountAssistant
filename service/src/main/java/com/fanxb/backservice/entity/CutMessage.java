package com.fanxb.backservice.entity;

import lombok.Data;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/3/28 15:13
 */
@Data
public class CutMessage {

    private Integer id;
    private Integer gameId;
    private Integer isOver;
    private Long createTime;
    private Long overTime;

    public CutMessage() {
    }

    public CutMessage(Integer id, Integer gameId, Integer isOver, Long createTime, Long overTime) {
        this.id = id;
        this.gameId = gameId;
        this.isOver = isOver;
        this.createTime = createTime;
        this.overTime = overTime;
    }
}
