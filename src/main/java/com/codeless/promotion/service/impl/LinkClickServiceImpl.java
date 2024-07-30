package com.codeless.promotion.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeless.promotion.entity.PromotionLink;
import com.codeless.promotion.entity.PromotionLinkClick;
import com.codeless.promotion.enums.PromotionLinkStatus;
import com.codeless.promotion.mapper.PromotionLinkClickMapper;
import com.codeless.promotion.mapper.PromotionLinkMapper;
import com.codeless.promotion.mq.messageBody.PromotionLinkClickMsg;
import com.codeless.promotion.mq.producer.CommentMsgSender;
import com.codeless.promotion.service.InitService;
import com.codeless.promotion.service.LinkClickService;
import com.codeless.promotion.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;

import static cn.hutool.core.text.CharSequenceUtil.isBlank;

@Slf4j
@Service
public class LinkClickServiceImpl extends ServiceImpl<PromotionLinkClickMapper, PromotionLinkClick> implements LinkClickService, InitService {


    @Resource
    private PromotionLinkClickMapper clickMapper;

    @Resource
    private PromotionLinkMapper linkMapper;

    @Resource
    private CommentMsgSender commentMsgSender;

    /**
     * 入库批次大小
     */
    private static final int REPORT_SIZE = 1;

    /**
     * 推广链接点击待入库队列
     */
    private final ConcurrentLinkedDeque<PromotionLinkClick> clickEvents = new ConcurrentLinkedDeque<>();


    /**
     * 服务初始化逻辑
     */
    @Override
    public void init() {

    }

    /**
     * 推广链接点击上报接口
     */
    @Override
    public void promotionLinkClick(String refid, String clid, HttpServletRequest request) {
        // 转换为点击打点数据
        PromotionLinkClick point = new PromotionLinkClick();
        point.setClId(clid);
        point.setRefId(refid);
        point.setCreateTime(DateUtil.date());
        String ip = HttpUtil.getIpAddress(request);
        point.setIpAddress(ip);

        // 保存到队列
        commentMsgSender.sendPromotionLinkClickMsg(point);
    }

    /**
     * 处理推广链接点击数据
     *
     * @wdy todo 单个入库改为批量入库
     */
    public void processPromotionLinkClick(PromotionLinkClick point) {
        log.info("开始处理 " + point);
        String clid = point.getClId();
        // @wdy todo 当前先从数据库查询，后续考虑性能的话，改成从缓存中查询，避免大量数据查询和读写卡数据库
        // 根据clid查询推广链接详细信息
        // @wdy 先走数据库  有时间再改成走缓存 LocalCache
        LambdaQueryWrapper<PromotionLink> linkWrapper = new LambdaQueryWrapper<>();
        // @wdy todo 是否走索引
        linkWrapper.eq(PromotionLink::getClIdForButton1, clid).or().eq(PromotionLink::getClIdForButton2, clid);
        // 根据clid查询推广链接详细信息
        List<PromotionLink> links = linkMapper.selectList(linkWrapper);
        if (CollUtil.isEmpty(links)) {
            return;
        }
        if (links.size() > 1) {
            log.error("clid {} 重复 ", clid);
            return;
        }
        PromotionLink currentLink = links.get(0);
        // 检查链接状态
        if (currentLink.getStatus() != PromotionLinkStatus.NORMAL.getValue()) {
            return;
        }
        point.setBusinessCode(currentLink.getBusinessCode());
        point.setCustomerEmail(currentLink.getCustomerEmail());

        // 放入待入库队列
        clickEvents.offer(point);

    }

    /**
     * 初始化批量入库点击事件
     */
    @PostConstruct
    public void initBatchInsertClickEvent() {
        Thread reportThread = new Thread(() -> {
            ArrayList<PromotionLinkClick> clickList = new ArrayList<>();
            for (; ; ) {
                try {
                    if (!clickEvents.isEmpty()) {
                        PromotionLinkClick event = clickEvents.pop();
                        if (event != null) {
                            clickList.add(event);
                            if (clickList.size() >= REPORT_SIZE) {
                                ArrayList<PromotionLinkClick> newArray = (ArrayList<PromotionLinkClick>) clickList.clone();
                                clickList.clear();
                                ThreadUtil.execAsync(() -> {
                                    try {
                                        clickMapper.insertBatchClickEvents(newArray);
                                    } catch (Exception e) {
                                        log.error("批量入库异常", e);
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("批量入库任务执行异常", e);
                }
            }

        }, "上报推荐请求线程");
        reportThread.start();
    }


}
