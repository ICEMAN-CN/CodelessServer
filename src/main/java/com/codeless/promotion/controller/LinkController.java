package com.codeless.promotion.controller;

import com.codeless.promotion.dto.JsonResult;
import com.codeless.promotion.service.LinkClickService;
import com.codeless.promotion.service.LinkManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


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
     * demo: http://daqingniu.com/codeless/link/click?refid=da845324be234f7e8785c665a91769cf&clid=81a6b4e3d1b942468a61e8f57d61feea
     * @param refid 链接来源ID
     * @param clid  链接标识
     * @return "ok"
     */
    @ApiOperation("推广链接列表")
    @GetMapping("/list")
    public JsonResult<String> getPromotionLinkList(@RequestBody ) {


        return JsonResult.ok("ok");
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
                                                 @RequestParam String clid) {


        return JsonResult.ok("ok");
    }




}
