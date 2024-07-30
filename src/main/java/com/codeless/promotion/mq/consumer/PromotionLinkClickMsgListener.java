package com.codeless.promotion.mq.consumer;

import com.codeless.promotion.entity.PromotionLinkClick;
import com.codeless.promotion.mq.messageBody.PromotionLinkClickMsg;
import com.codeless.promotion.service.LinkClickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import javax.annotation.Resource;

import static com.codeless.promotion.enums.MqTopicConstants.PROMOTION_LINK_CLICK;
import static com.codeless.promotion.enums.MqTopicConstants.USER_EVENT;


/**
 * 推广链接点击上报消息 通知到这里
 *
 * @author wangdongyang
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.group}-" + USER_EVENT + "-" + PROMOTION_LINK_CLICK,
        topic = USER_EVENT, selectorExpression = PROMOTION_LINK_CLICK, consumeThreadNumber = 20)
public class PromotionLinkClickMsgListener implements RocketMQListener<PromotionLinkClick> {

    @Resource
    private LinkClickService clickService;


    @Override
    public void onMessage(PromotionLinkClick msg) {
        log.info("【业务消息通知】推广链接点击上报消息 {}", msg.getClId());
        clickService.processPromotionLinkClick(msg);
        log.info("【业务消息通知】处理推广链接点击上报消息成功 {}", msg.getClId());
    }


}
