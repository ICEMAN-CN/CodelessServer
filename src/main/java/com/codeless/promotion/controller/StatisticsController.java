package com.codeless.promotion.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codeless.promotion.dto.JsonResult;
import com.codeless.promotion.entity.ClickStatistics;
import com.codeless.promotion.exception.ServiceException;
import com.codeless.promotion.mapper.ClickStatisticsMapper;
import com.codeless.promotion.service.LinkClickService;
import com.codeless.promotion.service.LinkManageService;
import com.codeless.promotion.vo.request.*;
import com.codeless.promotion.vo.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.hutool.core.text.CharSequenceUtil.isBlank;
import static com.codeless.promotion.controller.LinkController.EMAIL_REF_ID;
import static com.codeless.promotion.controller.LinkController.WEB_REF_ID;
import static com.codeless.promotion.enums.GlobalError.BAD_REQUEST;


/**
 *
 * @author : wangdongyang
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/statistics")
@Api(tags = "统计相关接口")
public class StatisticsController {

    @Resource
    private ClickStatisticsMapper mapper;

    @ApiOperation("天类型统计数据")
    @PostMapping("/day")
    public JsonResult<DayStatisticsResp> dayData(@RequestBody DayStatisticsReq req) {
        DayStatisticsResp resp = new DayStatisticsResp();
        List<DayStatisticsItem> finalItems = new ArrayList<>();
        List<ClickStatistics> dayStatistics = new ArrayList<>();
        LambdaQueryWrapper<ClickStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClickStatistics::getStatisticsType, req.getStatisticsType());
        wrapper.eq(ClickStatistics::getBusinessCode, req.getBusinessCode());
        wrapper.ge(ClickStatistics::getCreateTime, req.getBeginTime());
        DateTime endTime = DateUtil.endOfDay(req.getEndTime());
        wrapper.le(ClickStatistics::getCreateTime, endTime);
        dayStatistics = mapper.selectList(wrapper);
        // 处理日期
        dayStatistics.forEach(item -> {
            item.setCreateTime(DateUtil.beginOfDay(item.getCreateTime()));
        });



        // 将不同维度打平的统计数据，处理成前端需要的单条包含多维度数据的格式
        Map<String, List<ClickStatistics>> map = dayStatistics.stream().collect(Collectors.groupingBy(a -> a.getBusinessCode() + "_" + a.getCreateTime()));
        Map<String, DayStatisticsItem> statisticMap = new HashMap<>();
        // 来源统计没有按钮
        if (req.getStatisticsType().contains("RefDay")) {
            dayStatistics.forEach(a -> {
                String key = a.getBusinessCode() + "_" + a.getCreateTime();
                DayStatisticsItem item = new DayStatisticsItem();
                if (map.containsKey(key) && !statisticMap.containsKey(key)) {
                    item.setStatisticsType(a.getStatisticsType());
                    item.setDate(DateUtil.format(a.getCreateTime(), "yyyy-MM-dd"));
                    List<ClickStatistics> list = map.get(key);
                    list.forEach(b -> {
                        item.setBusinessCode(b.getBusinessCode());
                        if (b.getRefId().equals(EMAIL_REF_ID)) {
                            item.setEmailPv(b.getPv());
                            item.setEmailUv(b.getUv());
                        }
                        if (b.getRefId().equals(WEB_REF_ID)) {
                            item.setWebPv(b.getPv());
                            item.setWebUv(b.getUv());
                        }
                    });
                    statisticMap.put(key, item);
                    finalItems.add(item);
                }

            });
            List<String> dataDates = finalItems.stream().map(a -> a.getDate()).collect(Collectors.toList());
            List<DateTime> dateList = DateUtil.rangeToList(req.getBeginTime(), req.getEndTime(), DateField.DAY_OF_WEEK);
            dateList.forEach(date -> {
                if (!dataDates.contains(DateUtil.format(date, "yyyy-MM-dd"))) {
                    DayStatisticsItem item = new DayStatisticsItem();
                    item.setBusinessCode(req.getBusinessCode());
                    item.setStatisticsType(req.getStatisticsType());
                    item.setDate(DateUtil.format(date, "yyyy-MM-dd"));
                    finalItems.add(item);
                }
            });
            // dayStatistics 日期倒序排序
            finalItems.sort((o1, o2) -> {
                return o2.getDate().compareTo(o1.getDate());
            });
            resp.setList(finalItems);
            return JsonResult.ok(resp);
        }
        if (req.getStatisticsType().contains("ButtonDay")) {
            dayStatistics.forEach(a -> {
                String key = a.getBusinessCode() + "_" + a.getCreateTime();
                DayStatisticsItem item = new DayStatisticsItem();
                if (map.containsKey(key) && !statisticMap.containsKey(key)) {
                    item.setStatisticsType(a.getStatisticsType());
                    item.setDate(DateUtil.format(a.getCreateTime(), "yyyy-MM-dd"));
                    List<ClickStatistics> list = map.get(key);
                    list.forEach(b -> {
                        item.setBusinessCode(b.getBusinessCode());
                        if (b.getRefId().equals(EMAIL_REF_ID)) {
                            if (b.getClIdButton() == 1) {
                                item.setEmailButton1Pv(b.getPv());
                                item.setEmailButton1Uv(b.getUv());
                            } else if (b.getClIdButton() == 2) {
                                item.setEmailButton2Pv(b.getPv());
                                item.setEmailButton2Uv(b.getUv());
                            }
                        }
                        if (b.getRefId().equals(WEB_REF_ID)) {
                            if (b.getClIdButton() == 1) {
                                item.setWebButton1Pv(b.getPv());
                                item.setWebButton1Uv(b.getUv());
                            } else if (b.getClIdButton() == 2) {
                                item.setWebButton2Pv(b.getPv());
                                item.setWebButton2Uv(b.getUv());
                            }
                        }
                    });
                    statisticMap.put(key, item);
                    finalItems.add(item);
                }

            });
            List<String> dataDates = finalItems.stream().map(a -> a.getDate()).collect(Collectors.toList());
            List<DateTime> dateList = DateUtil.rangeToList(req.getBeginTime(), req.getEndTime(), DateField.DAY_OF_WEEK);
            dateList.forEach(date -> {
                if (!dataDates.contains(DateUtil.format(date, "yyyy-MM-dd"))) {
                    DayStatisticsItem item = new DayStatisticsItem();
                    item.setBusinessCode(req.getBusinessCode());
                    item.setStatisticsType(req.getStatisticsType());
                    item.setDate(DateUtil.format(date, "yyyy-MM-dd"));
                    finalItems.add(item);
                }
            });
            // dayStatistics 日期倒序排序
            finalItems.sort((o1, o2) -> {
                return o2.getDate().compareTo(o1.getDate());
            });
            if (req.getStatisticsType().equals("emailClickButtonTotal") || req.getStatisticsType().equals("emailClickTotalData")) {
                if (finalItems.size() > 10) {

                }
            }
            resp.setList(finalItems);
            return JsonResult.ok(resp);
        }
        return JsonResult.ok(resp);
    }

    @ApiOperation("总类型统计数据")
        @PostMapping("/total")
    public JsonResult<TotalStatisticsResp> totalData(@RequestBody TotalStatisticsReq req) {
        TotalStatisticsResp resp = new TotalStatisticsResp();
        resp.setBusinessCode(req.getBusinessCode());
        List<ClickStatistics> totalStatistics = new ArrayList<>();
        LambdaQueryWrapper<ClickStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClickStatistics::getStatisticsType, req.getStatisticsType());
        wrapper.eq(ClickStatistics::getBusinessCode, req.getBusinessCode());
        totalStatistics = mapper.selectList(wrapper);
        resp.setList(totalStatistics);
        return JsonResult.ok(resp);
    }


}
