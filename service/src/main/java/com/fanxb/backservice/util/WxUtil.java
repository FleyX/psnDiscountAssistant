package com.fanxb.backservice.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanxb.backservice.entity.output.BodyItem;
import com.fanxb.backservice.entity.output.SendMessageBody;
import com.fanxb.backservice.exception.CustomException;
import okhttp3.Request;

import java.util.HashMap;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/5/6 17:33
 */
public class WxUtil {

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    private static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s";

    /**
     * access_token key
     */
    public static final String ACCESS_TOKEN_KEY = "access_token";

    public static final String ERROR_CODE_KEY = "errcode";


    public static String getToken(String appId, String secret) {
        String url = String.format(ACCESS_TOKEN_URL, appId, secret);
        Request request = new Request.Builder().url(url).build();
        JSONObject obj = HttpUtil.request(request);
        if (obj.containsKey(ACCESS_TOKEN_KEY)) {
            return obj.getString(ACCESS_TOKEN_KEY);
        } else {
            throw new CustomException("获取token异常:" + obj);
        }
    }

    /**
     * Description: 给用户发送模板消息
     *
     * @param token      token
     * @param openId     用户openId
     * @param templateId 模板Id
     * @param page       用户点击跳转页面
     * @return boolean
     * @author fanxb
     * @date 2019/5/7 11:39
     */
    public static boolean sendMessage(String token, String formId, String openId, HashMap<String, BodyItem> data, String templateId, String page) {
        String url = String.format(SEND_TEMPLATE_URL, token);
        SendMessageBody body = new SendMessageBody();
        body.setAccessToken(token);
        body.setTouser(openId);
        body.setTemplateId(templateId);
        body.setPage(page);
        body.setFormId(formId);
        body.setData(data);
        JSONObject res = HttpUtil.post(url, JSON.toJSONString(body));
        return res.getInteger(ERROR_CODE_KEY) == 0;
    }
}
