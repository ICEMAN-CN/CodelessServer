package com.codeless.promotion.controller;

import cn.hutool.core.util.RandomUtil;
import com.codeless.promotion.dto.JsonResult;
import com.codeless.promotion.exception.ServiceException;
import com.codeless.promotion.service.LinkClickService;
import com.codeless.promotion.service.LinkManageService;
import com.codeless.promotion.vo.request.BatchAddPromotionLinkReq;
import com.codeless.promotion.vo.request.GenerateEmailAndWebUrlReq;
import com.codeless.promotion.vo.request.GetPromotionLinkListReq;
import com.codeless.promotion.vo.response.GenerateEmailAndWebUrlResp;
import com.codeless.promotion.vo.response.PromotionLinkListResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cn.hutool.core.text.CharSequenceUtil.isBlank;
import static com.codeless.promotion.enums.GlobalError.BAD_REQUEST;


/**
 *
 * @author : wangdongyang
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/link")
@Api(tags = "推广链接相关Controller")
public class LinkController {

    @Resource
    private LinkManageService manageService;

    @Resource
    private LinkClickService clickService;

    /**
     * web来源UUID
     */
    public static final String WEB_REF_ID = "da845324be234f7e8785c665a91769cf";

    /**
     * email来源UUID
     */
    public static final String EMAIL_REF_ID = "80442b807b5f29e573857b1432344248";

    @ApiOperation("批量生成email推广链接，因为有两个按钮，同一个邮件生成双份的链接")
    @PostMapping("/email/batchAdd")
    public JsonResult<Long> batchAddEmailPromotionLinkList(@RequestBody BatchAddPromotionLinkReq req) {
        req.setBusinessCode(req.getBusinessCode());
        if (!req.isValid()) {
            throw new ServiceException(BAD_REQUEST);
        }
        Long num = manageService.batchAddPromotionLinkList(req, true);
        return JsonResult.ok(num);
    }

    @ApiOperation("生成email内容")
    @PostMapping("/email/generateText")
    public JsonResult<GenerateEmailAndWebUrlResp> generateText(@RequestBody GenerateEmailAndWebUrlReq req) {
        if (!req.isValid()) {
            throw new ServiceException(BAD_REQUEST);
        }
        GenerateEmailAndWebUrlResp resp = manageService.generateText(req);
        return JsonResult.ok(resp);
    }

    @ApiOperation("批量增加web推广链接")
    @PostMapping("/web/batchAdd")
    public JsonResult<Long> batchAddWebPromotionLinkList(@RequestBody BatchAddPromotionLinkReq req) {
        Long num = manageService.batchAddPromotionLinkList(req, false);
        return JsonResult.ok(num);
    }


    @ApiOperation("推广链接列表")
    @PostMapping("/list")
    public JsonResult<PromotionLinkListResp> getPromotionLinkList(@RequestBody GetPromotionLinkListReq req ) {

        if (!req.isValid()) {
            throw new ServiceException(BAD_REQUEST);
        }
        PromotionLinkListResp resp = manageService.getPromotionLinkList(req);
        if (resp == null) {
            log.error("PromotionLinkList is null");
        }
        return JsonResult.ok(resp);
    }

    /**
     * demo: http://daqingniu.com/codeless/link/click?refid=da845324be234f7e8785c665a91769cf&clid=81a6b4e3d1b942468a61e8f57d61feea
     * @param refid 链接来源ID
     * @param clid  链接标识
     * @return "ok"
     */
    @ApiOperation("推广链接点击上报接口")
    @GetMapping("/click")
    public JsonResult<String> promotionLinkClick(@RequestParam String refid,
                                                 @RequestParam String clid,
                                                 HttpServletRequest request) {
        // 参数校验
        if (isBlank(refid) || isBlank(clid)) {
            throw new ServiceException(BAD_REQUEST);
        }
        clickService.promotionLinkClick(refid, clid, request);
        return JsonResult.ok("ok");
    }


    @ApiOperation("手动触发统计更新")
    @GetMapping("/manualStatistics")
    public JsonResult<String> manualStatistics() {
        clickService.manualUpdateStatisticsData();
        return JsonResult.ok("ok");
    }



}
