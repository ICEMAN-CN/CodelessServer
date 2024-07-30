package com.codeless.promotion.mq.producer;

import cn.hutool.core.bean.BeanUtil;
import com.codeless.promotion.entity.PromotionLinkClick;
import com.codeless.promotion.enums.MqTopicConstants;
import com.codeless.promotion.mq.messageBody.PromotionLinkClickMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static com.codeless.promotion.enums.MqTopicConstants.PROMOTION_LINK_CLICK;
import static com.codeless.promotion.enums.MqTopicConstants.USER_EVENT;


/**
 * 通用消息发送器
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentMsgSender {

    private final RocketMQTemplate rocketMQTemplate;

    /**
     * 发送推广链接点击事件
     */
    public void sendPromotionLinkClickMsg(PromotionLinkClick msg){
        // 构建消息
        String messageKey = "click_" + msg.getRefId() + "_" + msg.getClId();
        Message<?> message = MessageBuilder.withPayload(msg).setHeader(MqTopicConstants.KEYS, messageKey).build();
        SendResult result = rocketMQTemplate.syncSend( USER_EVENT + ":" + PROMOTION_LINK_CLICK, message);
        log.info("发送推广 " + result);
    }


}
