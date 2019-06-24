package com.fanxb.backservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/4/4 16:10
 */
@Component
public class Constant {

    public static String appId;

    public static String secret;

    private static String accessToken = null;

    private static long accessTokenExpireTime = 0;

    synchronized public static String getAccessToken() {
        if (accessTokenExpireTime < System.currentTimeMillis()) {
            accessToken = WxUtil.getToken(appId, secret);
            accessTokenExpireTime = System.currentTimeMillis() + (2 * 60 - 1) * 60 * 1000;

        }
        return accessToken;
    }

    @Value("${wx.appId}")
    public void setAppId(String appId) {
        Constant.appId = appId;
    }

    @Value("${wx.secret}")
    public void setSecret(String secret) {
        Constant.secret = secret;
    }

    public static boolean isDev;

    @Value("${spring.profiles.active}")
    public void setIsDev(String profiles) {
        isDev = !StringUtil.isEmpty(profiles) && profiles.contains("dev");
    }

    /**
     * 微信openId key
     */
    public static final String OPEN_ID_KEY = "openid";


    /**
     * 微信响应session id key
     */
    public static final String SESSION_ID_KEY = "session_key";
    /**
     * secret key
     */
    public static final String SECRET_KEY = "secret";

    /**
     * jwt key
     */
    public static final String JWT_KEY = "jwt-token";

    /**
     * userId key
     */
    public static final String USER_ID = "user_id";

    /**
     * jwt 过期时间，ms
     */
    public static int JWT_EXPIRE_TIME = 60 * 60 * 60 * 1000;

    /**
     * 验证码过期时间
     */
    public static int VERIFY_CODE_EXPIRE = 15 * 60 * 1000;

    /**
     * 邮箱
     */
    public static String MAIL_TYPE = "email";

    /**
     * 手机号
     */
    public static String PHONE_TYPE = "phone";

    public static String HEADER_FORM_ID = "formIdList";

    /**
     * 折扣消息推送id
     */
    public static final String CUT_MESSAGE_TEMPLATE_ID = "K3Ls-2e6gbR0GaNAzuL_ffoJfSqiiof-RindGkHq__k";


    /**
     * 获取用户信息key
     */
    public static String getUserInfoKey(int userId) {
        return "user_" + userId;
    }

    /**
     * 获取验证码key
     */
    public static String getVerifyCodeKey(int userId, String type, String value) {
        return "verify_" + userId + "_" + type + "_" + value;
    }

}
