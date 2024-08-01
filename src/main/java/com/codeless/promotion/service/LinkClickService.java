package com.codeless.promotion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codeless.promotion.entity.PromotionLinkClick;

import javax.servlet.http.HttpServletRequest;

public interface LinkClickService extends IService<PromotionLinkClick> {

    void promotionLinkClick(String refid, String clid, HttpServletRequest request);

    void processPromotionLinkClick(PromotionLinkClick point);

    void manualUpdateStatisticsData();
}
