package com.fanxb.backservice.entity;

import lombok.Data;

@Data
public class Server {

    private Integer serverId;
    private String name;
    private double exchangeRate;
    private String moneySymbol;
    private String listApi;
    private String gameDetailApi;


}
