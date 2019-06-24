package com.fanxb.backservice.entity.input;

import lombok.Data;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/3/21 17:10
 */
@Data
public class GameListParam {
    /**
     * 区域id
     */
    private int serverId;
    /**
     * 平台id
     */
    private String platform;

    /**
     * 游戏类别
     */
    private String type;
    /**
     * 语言
     */
    private String language;
    /**
     * 是否打折
     */
    private short isCut;
    /**
     * 排序关键词
     */
    private String orderKey;
    /**
     * 是否正序排列
     */
    private short isAsc;
    /**
     * 开始
     */
    private Integer start;
    /**
     * 条数
     */
    private Integer size;
}
