package com.fanxb.backservice.entity;

import lombok.Data;

/**
 * Description:
 *
 * @author fanxb
 * @date 2019/6/10 19:30
 */
@Data
public class Game {

    private Integer id;
    private Integer serverId;
    private String storeId;
    private String name;
    private String platform;
    private String gameContent;
    private String zhName;
    private String icon;
    private String description;
    private String language;
    private Long publishTime;
    private String type;
    private Integer currentPrice;
    private Integer rmbPrice;
    private Integer cutPercent;
    private Integer plusPrice;
    private Integer rmbPlusPrice;
    private Integer plusCutPercent;
    private Integer originPrice;
    private Long cutOverTime;
    private Integer isPlus;
    private Integer isLowest;
    private Integer psnScore;
    private Integer psnScoreNum;
    private Integer mediaScore;
    private Long createTime;
    private Long updateTime;
    private String parentStoreId;
    private Integer valid;

}
