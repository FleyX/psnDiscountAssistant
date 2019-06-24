package com.fanxb.backservice.service;

import com.fanxb.backservice.dao.AttentionPriceDao;
import com.fanxb.backservice.entity.AttentionPrice;
import com.fanxb.backservice.entity.UserContext;
import com.fanxb.backservice.util.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/4/10 19:41
 */
@Service
public class AttentionPriceService {

    @Autowired
    private AttentionPriceDao attentionPriceDao;

    /**
     * Description: 新增关注
     *
     * @author fanxb
     * @date 2019/4/12 16:39
     * @param item item
     */
    public void addOne(AttentionPrice item) {
        UserContext context = UserContextHolder.get();
        item.setCreateTime(System.currentTimeMillis());
        item.setUserId(context.getUser().getUserId());

        this.attentionPriceDao.addOne(item);
    }

    /**
     * Description: 取消关注
     *
     * @author fanxb
     * @date 2019/4/12 16:40
     * @param gameId gameId
     */
    public void deleteOne(int gameId){
        int userId = UserContextHolder.get().getUser().getUserId();
        this.attentionPriceDao.deleteOne(gameId,userId);
    }

}
