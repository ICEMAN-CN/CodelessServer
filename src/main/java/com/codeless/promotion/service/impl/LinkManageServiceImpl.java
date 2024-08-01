package com.codeless.promotion.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeless.promotion.entity.PromotionLink;
import com.codeless.promotion.mapper.PromotionLinkMapper;
import com.codeless.promotion.service.InitService;
import com.codeless.promotion.service.LinkManageService;
import com.codeless.promotion.vo.request.BatchAddPromotionLinkReq;
import com.codeless.promotion.vo.request.GenerateEmailAndWebUrlReq;
import com.codeless.promotion.vo.request.GetPromotionLinkListReq;
import com.codeless.promotion.vo.response.GenerateEmailAndWebUrlResp;
import com.codeless.promotion.vo.response.PromotionLinkListResp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.codeless.promotion.controller.LinkController.EMAIL_REF_ID;
import static com.codeless.promotion.controller.LinkController.WEB_REF_ID;

@Slf4j
@Service
@AllArgsConstructor
public class LinkManageServiceImpl extends ServiceImpl<PromotionLinkMapper, PromotionLink> implements LinkManageService, InitService {

    @Resource
    private PromotionLinkMapper linkMapper;

    /**
     * web url 模板
     * @wdy todo 打开web端页面，然后vue从链接获取参数，拼接到网页按钮行为上
     */
    public static final String WEB_URL_TEMPLATE = "http://49.232.70.73/codeless/#/homepage?refid=" + WEB_REF_ID + "&clidb1=ccclid1&clidb2=ccclid2";
//    http://49.232.70.73/codeless/#/homepage?refid=80442b807b5f29e573857b1432344248&clidb1=fe2bb696a730486e93108ebc4ab6be5c&clidb2=23d5150a0c054923bf78630396d30358
    /**
     * email 文本 模板
     */
    public static final String EMAIL_TEXT_TEMPLATE = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "    <meta charset=\"UTF-8\">" +
            "    <title>Title</title>" +
            "</head>" +
            "<body>" +
            "<div style=\"font-size: 20px; text-align: center\">" +
            "    <p>亲爱的，<br>我们目前有以下最新产品，非常满足您的需求，推荐您了解一下</p>" +
            "</div>" +
            "<div style=\"font-size: 20px; text-align: center\">" +
            "    <a target=\"_blank\"" +
            "       style=\"background-color:#009B5B;border-bottom:0px solid #009B5B;border-left:0px solid #009B5B;border-radius:4px;border-right:0px solid #009B5B;border-top:0px solid #009B5B;color:#FFFFFF;display:inline-block;font-family:'Arial', Arial, Sans-serif;font-size:16px;font-weight:400;mso-border-alt:none;padding-bottom:10px;padding-top:10px;text-align:center;text-decoration:none;width:auto;word-break:keep-all;\"" +
            "       href=\"http://49.232.70.73/api/codeless/link/click?refid=emailrefid&clid=b1clid\"><span" +
            "            style=\"word-break: break-word; padding-left: 44px; padding-right: 44px; font-size: 16px; display: inline-block; letter-spacing: normal;\"><span" +
            "            style=\"word-break: break-word; line-height: 24px;\">点开看看</span></span></a>" +
            "" +
            "    <a target=\"_blank\"" +
            "       style=\"background-color:#909399;border-bottom:0px solid #909399;border-left:0px solid #909399;border-radius:4px;border-right:0px solid #909399;border-top:0px solid #009B5B;color:#FFFFFF;display:inline-block;font-family:'Arial', Arial, Sans-serif;font-size:16px;font-weight:400;mso-border-alt:none;padding-bottom:10px;padding-top:10px;text-align:center;text-decoration:none;width:auto;word-break:keep-all;\"" +
            "       href=\"http://49.232.70.73/api/codeless/link/click?refid=emailrefid&clid=b2clid\"><span" +
            "            style=\"word-break: break-word; padding-left: 44px; padding-right: 44px; font-size: 16px; display: inline-block; letter-spacing: normal;\"><span" +
            "            style=\"word-break: break-word; line-height: 24px;\">不感兴趣</span></span></a>" +
            "</div>>" +
            "</body>" +
            "</html>";

    /**
     * 随机生成100邮箱
     * @wdy todo 暂时随机生成，后续根据真实业务获取客户信息
     */
    public static List<String> getRandomEmailList() {
        List<String> emailList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            emailList.add(RandomUtil.randomString(3) + RandomUtil.randomNumbers(3) + "@gmail.com");
        }
        return emailList;
    }


    /**
     * 服务初始化逻辑
     */
    @Override
    public void init() {
        // 数据量很小不需要进行初始化加载
        ;
    }

    /**
     * 获取推广链接列表
     */
    @Override
    public PromotionLinkListResp getPromotionLinkList(GetPromotionLinkListReq req) {
        PromotionLinkListResp resp = new PromotionLinkListResp();
        Integer pageIndex = req.getPageIndex();
        Integer pageSize = req.getPageSize();

        // 查询总数
        LambdaQueryWrapper<PromotionLink> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PromotionLink::getBusinessCode, req.getBusinessCode());
        wrapper.orderByDesc(PromotionLink::getId);

        // 分页
        IPage<PromotionLink> page = new Page<>();
        page.setCurrent(pageIndex.longValue() + 1);
        page.setSize(pageSize);

        IPage<PromotionLink> pages = this.page(page, wrapper);
        resp.setList(pages.getRecords());
        resp.setTotalPage(pages.getPages());
        resp.setTotalCount(pages.getTotal());
        return resp;
    }

    /**
     * 批量生成推广链接
     */

    @Override
    public Long batchAddPromotionLinkList(BatchAddPromotionLinkReq req,  boolean emailLink) {
        DateTime now = DateUtil.date();
        String businessCode = req.getBusinessCode();
        List<PromotionLink> listToAdd = new ArrayList<>();
        if (emailLink) {
            for (String email : getRandomEmailList()) {
                PromotionLink link = new PromotionLink();
                link.setBusinessCode(businessCode);
                link.setCustomerEmail(email);
                link.setRefId(EMAIL_REF_ID);
                link.setClIdForButton1(IdUtil.simpleUUID());
                link.setClIdForButton2(IdUtil.simpleUUID());
                link.setCreateTime(now);
                listToAdd.add(link);
            }
        } else {
            PromotionLink link = new PromotionLink();
            link.setBusinessCode(businessCode);
            link.setCustomerEmail(null);
            link.setRefId(WEB_REF_ID);
            link.setClIdForButton1(IdUtil.simpleUUID());
            link.setClIdForButton2(IdUtil.simpleUUID());
            link.setCreateTime(now);
            listToAdd.add(link);
        }
        this.saveBatch(listToAdd);
        return (long) listToAdd.size();
    }


    @Override
    public GenerateEmailAndWebUrlResp generateText(GenerateEmailAndWebUrlReq req) {
        GenerateEmailAndWebUrlResp resp = new GenerateEmailAndWebUrlResp();
        resp.setEmailText(EMAIL_TEXT_TEMPLATE.replace("emailrefid", EMAIL_REF_ID).replace("b1clid", req.getClIdForButton1()).replace("b2clid", req.getClIdForButton2()));
        resp.setEmailText(resp.getEmailText().replace("\"", "'"));
        resp.setWebUrl(WEB_URL_TEMPLATE.replace("ccclid1", req.getClIdForButton1()).replace("ccclid2", req.getClIdForButton2()));
        return resp;
    }


}
