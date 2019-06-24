package com.fanxb.backservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanxb.backservice.dao.UserDao;
import com.fanxb.backservice.entity.MailInfo;
import com.fanxb.backservice.entity.User;
import com.fanxb.backservice.entity.input.ChangeContactInfo;
import com.fanxb.backservice.exception.CustomException;
import com.fanxb.backservice.util.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/4/4 15:57
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserDao userDao;


    private static final String LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    /**
     * 微信登陆
     *
     * @param code 微信code
     * @return
     */
    public Map<String, Object> login(String code) {
        String url = String.format(LOGIN_URL, Constant.appId, Constant.secret, code);
        JSONObject obj;
        if (Constant.isDev) {
            obj = JSONObject.parseObject("{\"openid\":\"oxZgQ0fxfeI5ro9Y0EchmKkpBuEg\",\"session_key\":\"asdfasdf\"}");
        } else {
            Request request = new Request.Builder().url(url).build();
            obj = HttpUtil.request(request);
        }
        if (obj.containsKey(Constant.OPEN_ID_KEY)) {
            String openId = obj.getString(Constant.OPEN_ID_KEY);
            User user = userDao.getByOpenId(openId);
            if (user == null) {
                user = new User();
                user.setOpenId(openId);
                userDao.addOne(user);
                user = userDao.getByUserId(user.getUserId());
            }
            Map<String, String> map = new HashMap<>(10);
            map.put(Constant.USER_ID, user.getUserId().toString());
            String secret = UUID.randomUUID().toString().replaceAll("-", "");
            String jwt = JwtUtil.encode(map, secret, Constant.JWT_EXPIRE_TIME);
            //存入redis
            map.put(Constant.SESSION_ID_KEY, obj.getString(Constant.SESSION_ID_KEY));
            map.put(Constant.SECRET_KEY, secret);
            RedisUtil.set(jwt, JSON.toJSONString(map), Constant.JWT_EXPIRE_TIME);
            userDao.updateLastLoginTime(user.getUserId(), System.currentTimeMillis());
            log.info("{},{}登陆成功", user.getUserId(), user.getOpenId());
            Map<String, Object> res = new HashMap<>(2);
            res.put("userInfo", user);
            res.put(Constant.JWT_KEY, jwt);
            return res;
        } else {
            throw new CustomException("login失败");
        }
    }

    /**
     * Description: 根据userId获取user信息，先从redis，再从数据库
     *
     * @param userId userId
     * @return com.fanxb.backservice.entity.User
     * @author fanxb
     * @date 2019/4/12 10:36
     */
    public User getUserById(int userId) {
        String key = Constant.getUserInfoKey(userId);
        User user = RedisUtil.get(key, User.class);
        if (user == null) {
            user = userDao.getByUserId(userId);
            RedisUtil.set(key, JSON.toJSONString(user));
        }
        return user;
    }

    /**
     * Description:发送验证码
     *
     * @param type  类别：邮箱，手机号
     * @param value 值
     * @return void
     * @author fanxb
     * @date 2019/4/16 13:29
     */
    public void sendVerifyCode(String type, String value) {
        String randomStr = StringUtil.getRandomString(6, 2);
        String text = "您的验证码为：" + randomStr + ",请注意此验证码有效期为15分钟\n来自psn折扣小助手";
        if (Constant.MAIL_TYPE.equals(type)) {
            MailUtil.sendTextMail(new MailInfo(value, "邮件验证码", text));
        } else if (Constant.PHONE_TYPE.equals(type)) {
            throw new CustomException("暂不支持手机号");
        } else {
            throw new CustomException("不支持的类型");
        }
        String key = Constant.getVerifyCodeKey(UserContextHolder.get().getUser().getUserId(), type, value);
        RedisUtil.set(key, randomStr, Constant.VERIFY_CODE_EXPIRE);
    }

    /**
     * Description:修改联系方式
     *
     * @param info info
     * @author fanxb
     * @date 2019/4/16 14:44
     */
    public void updateContactInfo(ChangeContactInfo info) {
        if (!(Constant.MAIL_TYPE.equals(info.getType()) || Constant.PHONE_TYPE.equals(info.getType()))) {
            throw new CustomException("不支持的类型");
        }
        User user = UserContextHolder.get().getUser();
        String key = Constant.getVerifyCodeKey(UserContextHolder.get().getUser().getUserId(), info.getType(), info.getValue());
        String realCode = RedisUtil.get(key, String.class);
        if (StringUtil.isEmpty(realCode)) {
            throw new CustomException("验证码已过期");
        }
        if (!realCode.equals(info.getVerifyCode())) {
            throw new CustomException("验证码错误");
        }
        this.userDao.updateContactInfo(user.getUserId(), info.getType(), info.getValue());
    }
}
