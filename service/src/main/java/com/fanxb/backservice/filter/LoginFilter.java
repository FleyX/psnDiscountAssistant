package com.fanxb.backservice.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.fanxb.backservice.dao.FormKeyDao;
import com.fanxb.backservice.dao.UrlDao;
import com.fanxb.backservice.entity.FormKey;
import com.fanxb.backservice.entity.Url;
import com.fanxb.backservice.entity.User;
import com.fanxb.backservice.entity.UserContext;
import com.fanxb.backservice.entity.output.Result;
import com.fanxb.backservice.service.UserService;
import com.fanxb.backservice.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/4/10 19:46
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "permissionFilter")
@Slf4j
@Order(10000)
public class LoginFilter implements Filter {

    @Value("${server.servlet.context-path}")
    private String urlPrefix;

    @Autowired
    private UrlDao urlDao;

    @Autowired
    private FormKeyDao formKeyDao;

    @Autowired
    private UserService userService;


    private List<Url> publicUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }


    private static AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        UserContextHolder.remove();
        HttpServletRequest request1 = (HttpServletRequest) request;
        if (this.publicUrl == null) {
            this.publicUrl = this.urlDao.getPublicUrl();
        }
        boolean isOk = false;
        String requestUrl = request1.getRequestURI().replace(urlPrefix, "");
        String requestMethod = request1.getMethod();
        for (Url url : publicUrl) {
            if (requestMethod.equalsIgnoreCase(url.getMethod()) && matcher.match(url.getUrl(), requestUrl)) {
                isOk = true;
                break;
            }
        }
        if (!isOk) {
            isOk = this.checkJwt(request1.getHeader(Constant.JWT_KEY));
        }
//        isOk = true;
        if (isOk) {
            //检查fromId，并存到数据库
            this.checkFormId(request1);
            int userId;
            if (UserContextHolder.get() != null) {
                userId = UserContextHolder.get().getUser().getUserId();
            } else {
                userId = 0;
            }
            log.info("{},{},{}", HttpUtil.getIpAddr(request1), request1.getRequestURI(), userId);
            //记录用户访问情况
            chain.doFilter(request, response);
        } else {
            log.error("jwt解密失败:{}", request1.getHeader(Constant.JWT_KEY));
            HttpServletResponse response1 = (HttpServletResponse) response;
            response1.setStatus(HttpStatus.OK.value());
            response1.setContentType("application/json");
            response1.setCharacterEncoding("utf-8");
            response1.getWriter().write(JSON.toJSONString(Result.unAuth()));
        }
    }

    /**
     * Description: 检查jwt
     *
     * @param jwt jwt字符串
     * @return boolean
     * @author fanxb
     * @date 2019/4/12 11:00
     */
    private boolean checkJwt(String jwt) {
        if (StringUtil.isEmpty(jwt)) {
            return false;
        }
        String resStr = RedisUtil.redisTemplate.opsForValue().get(jwt);
        if (StringUtil.isEmpty(resStr)) {
            return false;
        }
        try {
            JSONObject obj = JSONObject.parseObject(resStr);
            Map<String, Claim> map = JwtUtil.decode(jwt, obj.getString(Constant.SECRET_KEY));
            int userId = Integer.valueOf(map.get(Constant.USER_ID).asString());
            UserContext context = new UserContext();
            context.setJwt(jwt);
            context.setSessionId(obj.getString(Constant.SESSION_ID_KEY));
            context.setUser(userService.getUserById(userId));
            UserContextHolder.set(context);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("jwt解密失败：{},{}", jwt, resStr);
            return false;
        }
    }

    /**
     * Description: 从请求头中获取formIdList,并插入数据库
     *
     * @param request 请求头
     * @return void
     * @author fanxb
     * @date 2019/5/6 16:39
     */
    private void checkFormId(HttpServletRequest request) {
        String str = request.getHeader(Constant.HEADER_FORM_ID);
        if (StringUtil.isEmpty(str)) {
            return;
        }
        List<FormKey> formKeyList = JSON.parseArray(str, FormKey.class);
        int userId = UserContextHolder.get().getUser().getUserId();
        formKeyList.forEach(item -> item.setUserId(userId));
        this.formKeyDao.insertMany(formKeyList);
    }
}
