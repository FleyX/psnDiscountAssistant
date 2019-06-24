package com.fanxb.backservice.entity;


import lombok.Data;

@Data
public class PriceHistory {
    private Long date;
    private Integer gameId;
    private Integer price;
    private Integer plusPrice;
}
