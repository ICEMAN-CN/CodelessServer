package com.codeless.promotion.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeless.promotion.entity.ClickStatistics;
import com.codeless.promotion.entity.PromotionLink;
import com.codeless.promotion.entity.PromotionLinkClick;
import com.codeless.promotion.enums.PromotionLinkStatus;
import com.codeless.promotion.mapper.ClickStatisticsMapper;
import com.codeless.promotion.mapper.PromotionLinkClickMapper;
import com.codeless.promotion.mapper.PromotionLinkMapper;
import com.codeless.promotion.mq.producer.CommentMsgSender;
import com.codeless.promotion.service.InitService;
import com.codeless.promotion.service.LinkClickService;
import com.codeless.promotion.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LinkClickServiceImpl extends ServiceImpl<PromotionLinkClickMapper, PromotionLinkClick> implements LinkClickService, InitService {


    @Resource
    private PromotionLinkClickMapper clickMapper;

    @Resource
    private ClickStatisticsMapper statisticsMapper;

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
            log.error("点击的clid不存在，非法数据，不处理 " + clid);
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
        // 查看是点击的按钮编号
        point.setClIdButton(currentLink.getClIdForButton1().equals(clid) ? 1 : 2);
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


    /**
     * 定时任务计算链接点击数据
     * 每小时更新总统计数据
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void calculateTotalStatisticsData() {
        try {
            log.info("【定时任务】小时任务-计算链接点击数据，开始 {}", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));

            // 清空历史数据
            LambdaQueryWrapper<ClickStatistics> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(ClickStatistics::getId);
            wrapper.in(ClickStatistics::getStatisticsType, "repeatOpenButtonTotal", "repeatOpenRefTotal",
                    "firstOpenButtonTotal", "firstOpenRefTotal", "emailClickTotal", "emailClickButtonTotal");
            List<ClickStatistics> currentList = statisticsMapper.selectList(wrapper);
            log.error(" currentList " + currentList);
            currentList = CollUtil.distinct(currentList);
            log.error(" currentList distinct " + currentList);
            if (CollUtil.isNotEmpty(currentList)) {
                List<Integer> currentIds = currentList.stream().map(ClickStatistics::getId).collect(Collectors.toList());
                log.error(" currentIds " + currentIds);
                statisticsMapper.deleteBatchIds(currentIds);
            }

            List<ClickStatistics> list1 =  clickMapper.repeatOpenButtonTotal();
            list1.forEach(repeatOpenButtonTotalData -> {
                repeatOpenButtonTotalData.setStatisticsType("repeatOpenButtonTotal");
                repeatOpenButtonTotalData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(repeatOpenButtonTotalData);
            });


            List<ClickStatistics> list2 =  clickMapper.repeatOpenRefTotal();
            list2.forEach(repeatOpenRefTotalData -> {
                repeatOpenRefTotalData.setStatisticsType("repeatOpenRefTotal");
                repeatOpenRefTotalData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(repeatOpenRefTotalData);
            });


            List<ClickStatistics> list3 =  clickMapper.firstOpenButtonTotal();
            list3.forEach(firstOpenButtonTotalData -> {
                firstOpenButtonTotalData.setStatisticsType("firstOpenButtonTotal");
                firstOpenButtonTotalData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(firstOpenButtonTotalData);
            });


            List<ClickStatistics> list4 =  clickMapper.firstOpenRefTotal();
            list4.forEach(firstOpenRefTotalData -> {
                firstOpenRefTotalData.setStatisticsType("firstOpenRefTotal");
                firstOpenRefTotalData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(firstOpenRefTotalData);
            });

            List<ClickStatistics> list5 =  clickMapper.emailClickTotal();
            list5.forEach(emailClickTotalData -> {
                emailClickTotalData.setStatisticsType("emailClickTotal");
                emailClickTotalData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(emailClickTotalData);
            });


            List<ClickStatistics> list6 =  clickMapper.emailClickButtonTotal();
            list6.forEach(emailClickButtonTotalData -> {
                emailClickButtonTotalData.setStatisticsType("emailClickButtonTotal");
                emailClickButtonTotalData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(emailClickButtonTotalData);
            });

            log.info("【定时任务】小时任务-计算链接点击数据，完成 {}", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));
        } catch (Exception e) {
            log.error("小时任务-定时任务计算链接点击数据异常", e);
        }
    }

    /**
     * 定时任务计算链接点击数据
     * 每天更新日统计数据
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void calculateDayStatisticsData() {
        try {
            log.info("【定时任务】天任务-计算链接点击数据，开始 {}", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));

            DateTime now = DateUtil.date();
            DateTime yesterday = DateUtil.beginOfDay(DateUtil.offsetDay(DateUtil.date(), -1));
            DateTime today = DateUtil.beginOfDay(now);
            String begin = DateUtil.format(yesterday, DatePattern.NORM_DATETIME_PATTERN);
            String end = DateUtil.format(today, DatePattern.NORM_DATETIME_PATTERN);

            List<ClickStatistics> list1 =  clickMapper.repeatOpenButtonDay(begin, end);
            list1.forEach(repeatOpenButtonDayData -> {
                repeatOpenButtonDayData.setStatisticsType("repeatOpenButtonDay");
                repeatOpenButtonDayData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(repeatOpenButtonDayData);
            });


            List<ClickStatistics> list2 =  clickMapper.repeatOpenRefDay(begin, end);
            list2.forEach(repeatOpenRefDayData -> {
                repeatOpenRefDayData.setStatisticsType("repeatOpenRefDay");
                repeatOpenRefDayData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(repeatOpenRefDayData);
            });


            List<ClickStatistics> list3 =  clickMapper.firstOpenButtonDay(begin, end);
            list3.forEach(firstOpenButtonDayData -> {
                firstOpenButtonDayData.setStatisticsType("firstOpenButtonDay");
                firstOpenButtonDayData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(firstOpenButtonDayData);
            });


            List<ClickStatistics> list4 =  clickMapper.firstOpenRefDay(begin, end);
            list4.forEach(firstOpenRefDayData -> {
                firstOpenRefDayData.setStatisticsType("firstOpenRefDay");
                firstOpenRefDayData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(firstOpenRefDayData);
            });


            log.info("【定时任务】天任务-计算链接点击数据，完成 {}", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));

        } catch (Exception e) {
            log.error("天任务-定时任务计算链接点击数据异常", e);
        }
    }

    public void calculateDayStatisticsData2() {
        try {
            log.info("【定时任务】天任务-计算链接点击数据，开始 {}", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));

            DateTime now = DateUtil.date();
            DateTime yesterday = DateUtil.beginOfDay(now);
            DateTime yesterday2 = DateUtil.beginOfDay(DateUtil.offsetDay(DateUtil.date(), 1));
            DateTime today = DateUtil.beginOfDay(now);
            String begin = DateUtil.format(yesterday, DatePattern.NORM_DATETIME_PATTERN);
            String end = DateUtil.format(yesterday2, DatePattern.NORM_DATETIME_PATTERN);
            begin = "2024-07-31 00:00:00";
            begin = "2024-07-30 00:00:00";
            end = "2024-07-31 00:00:00";
            List<ClickStatistics> list1 =  clickMapper.repeatOpenButtonDay(begin, end);
            list1.forEach(repeatOpenButtonDayData -> {
                repeatOpenButtonDayData.setStatisticsType("repeatOpenButtonDay");
                repeatOpenButtonDayData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(repeatOpenButtonDayData);
            });


            List<ClickStatistics> list2 =  clickMapper.repeatOpenRefDay(begin, end);
            list2.forEach(repeatOpenRefDayData -> {
                repeatOpenRefDayData.setStatisticsType("repeatOpenRefDay");
                repeatOpenRefDayData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(repeatOpenRefDayData);
            });


            List<ClickStatistics> list3 =  clickMapper.firstOpenButtonDay(begin, end);
            list3.forEach(firstOpenButtonDayData -> {
                firstOpenButtonDayData.setStatisticsType("firstOpenButtonDay");
                firstOpenButtonDayData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(firstOpenButtonDayData);
            });


            List<ClickStatistics> list4 =  clickMapper.firstOpenRefDay(begin, end);
            list4.forEach(firstOpenRefDayData -> {
                firstOpenRefDayData.setStatisticsType("firstOpenRefDay");
                firstOpenRefDayData.setCreateTime(DateUtil.date());
                statisticsMapper.insert(firstOpenRefDayData);
            });


            log.info("【定时任务】天任务-计算链接点击数据，完成 {}", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));

        } catch (Exception e) {
            log.error("天任务-定时任务计算链接点击数据异常", e);
        }
    }

    /**
     * 手动更新统计数据
     */
    @Override
    public void manualUpdateStatisticsData() {
        this.calculateDayStatisticsData2();
        this.calculateTotalStatisticsData();
    }


}
