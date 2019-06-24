package com.fanxb.backservice.service;

import com.fanxb.backservice.dao.*;
import com.fanxb.backservice.entity.*;
import com.fanxb.backservice.entity.output.BodyItem;
import com.fanxb.backservice.util.*;
import com.fanxb.backservice.util.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/3/28 15:28
 */
@Service
@Slf4j
public class CutMessageService {

    private static final String ONCE_CUT = "once_cut";
    private static final String LOWEST = "lowest";
    private static final String LOWER = "lower";

    @Autowired
    private CutMessageDao cutMessageDao;
    @Autowired
    private AttentionPriceDao attentionPriceDao;
    @Autowired
    private FormKeyDao formKeyDao;

    private static HashMap<Integer, List<FormKey>> formIdMap;

    /**
     * Description: 检测数据，并进行推送
     *
     * @author fanxb
     * @date 2019/3/28 15:36
     */
    @Scheduled(fixedDelay = 10 * 60 * 1000)
    private void run() throws Exception {
        formIdMap = new HashMap<>(100);
        long time = System.currentTimeMillis();
        //清理过期formId
        formKeyDao.clearExpire(time);
        List<CutMessage> messageList = this.cutMessageDao.getUnOverList(time);
        ThreadPoolExecutor executor = ThreadPoolFactory.createPool(5, 5, 1000, 2000, "sendCutMessage");
        for (CutMessage message : messageList) {
            executor.execute(() -> dealOne(message));
//            dealOne(message);
        }
        while (true) {
            if (executor.getActiveCount() == 0) {
                executor.shutdownNow();
                break;
            } else {
                TimeUnit.SECONDS.sleep(2);
            }
        }
    }

    /**
     * Description: 向订阅此游戏折扣信息的用户推送数据
     *
     * @param message message
     * @author fanxb
     * @date 2019/3/28 16:33
     */
    private void dealOne(CutMessage message) {
        //获取订阅信息
        List<AttentionPriceInfo> infoList = this.attentionPriceDao.getByGameId(message.getGameId());
        infoList.forEach(item -> {
            switch (item.getType()) {
                case LOWEST:
                    if (item.getIsLowest() <= 0) {
                        break;
                    }
                case LOWER:
                    if (item.getCurrentPrice() > item.getValue()) {
                        break;
                    }
                case ONCE_CUT:
                    this.sendToOne(item);
                    break;
                default:
                    log.error("不支持的推送类别：{}", item.getType());
                    break;
            }
        });
        this.cutMessageDao.setOver(message.getId(), System.currentTimeMillis());
    }

    /**
     * Description: 推送数据
     *
     * @param info 详细信息
     * @author fanxb
     * @date 2019/3/28 17:18
     */
    private void sendToOne(AttentionPriceInfo info) {
        String str1 = String.format("%s 打折啦，%d%%折扣", info.getName(), info.getCutPercent());
        String str2 = "折后价格为RMB:" + info.getRmbPrice() / 100.0;
        Data data = this.getData(info.getUserId());
        //模板消息推送
        if (data.formKey != null) {
            try {
                HashMap<String, BodyItem> map = new HashMap<>(3);
                map.put("keyword1", new BodyItem(str1));
                map.put("keyword2", new BodyItem(str2));
                String page = String.format("/pages/index/detail/main?id=%d&serverId=%d", info.getGameId(), info.getServerId());
                WxUtil.sendMessage(data.token, data.formKey.getValue(), info.getOpenId(), map
                        , Constant.CUT_MESSAGE_TEMPLATE_ID, page);
                //清理fromId
                formKeyDao.deleteByFormKeyId(data.formKey.getFormKeyId());
            } catch (Exception e) {
                log.error("模板消息推送失败", e);
            }
        }
        //邮件推送
        try {
            MailUtil.sendTextMail(new MailInfo(info.getEmail(), "折扣提醒", str1 + "/n" + str2));
        } catch (Exception e) {
            log.error("邮件消息推送失败", e);
        }
    }

    /**
     * Description: 获取必须的数据
     *
     * @param userId userId
     * @return Data
     * @author fanxb
     * @date 2019/5/6 17:56
     */
    private Data getData(int userId) {
        Data data = new Data();
        data.token = Constant.getAccessToken();
        //检查formId是否充足
        synchronized (CutMessageService.class) {
            List<FormKey> formIdList = formIdMap.get(userId);
            if (formIdList == null || formIdList.size() == 0) {
                formIdList = formKeyDao.getByUserId(userId);
                formIdMap.put(userId, formIdList);

            }
            if (formIdList.size() > 0) {
                data.formKey = formIdList.remove(0);
            }
        }
        return data;
    }

    class Data {
        String token;
        FormKey formKey;
    }
}
