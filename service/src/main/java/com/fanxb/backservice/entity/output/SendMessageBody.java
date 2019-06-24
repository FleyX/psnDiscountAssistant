package com.fanxb.backservice.entity.output;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.HashMap;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/5/7 13:25
 */
@Data
public class SendMessageBody {
    @JSONField(name = "access_token")
    private String accessToken;
    private String touser;
    @JSONField(name = "template_id")
    private String templateId;
    private String page;
    @JSONField(name = "form_id")
    private String formId;
    private HashMap<String, BodyItem> data;
}

