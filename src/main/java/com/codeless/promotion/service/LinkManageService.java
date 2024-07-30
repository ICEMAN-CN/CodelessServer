package com.codeless.promotion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codeless.promotion.entity.PromotionLink;
import com.codeless.promotion.vo.request.BatchAddPromotionLinkReq;
import com.codeless.promotion.vo.request.GenerateEmailAndWebUrlReq;
import com.codeless.promotion.vo.request.GetPromotionLinkListReq;
import com.codeless.promotion.vo.response.GenerateEmailAndWebUrlResp;
import com.codeless.promotion.vo.response.PromotionLinkListResp;

public interface LinkManageService extends IService<PromotionLink> {

    PromotionLinkListResp getPromotionLinkList(GetPromotionLinkListReq req);

    Long batchAddPromotionLinkList(BatchAddPromotionLinkReq req, boolean emailLink);

    GenerateEmailAndWebUrlResp generateText(GenerateEmailAndWebUrlReq req);

}