package com.fanxb.backservice.dao;

import com.fanxb.backservice.entity.Url;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/4/11 16:56
 */
@Component
public interface UrlDao {

    List<Url> getPublicUrl();
}
