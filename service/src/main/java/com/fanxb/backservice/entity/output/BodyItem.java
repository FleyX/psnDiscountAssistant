package com.fanxb.backservice.entity.output;

import lombok.Data;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/5/7 13:32
 */
@Data
public class BodyItem {
    private String value;

    public BodyItem() {
    }

    public BodyItem(String value) {
        this.value = value;
    }
}
