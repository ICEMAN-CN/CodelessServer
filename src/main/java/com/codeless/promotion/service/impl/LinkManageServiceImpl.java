package com.codeless.promotion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeless.promotion.entity.PromotionLink;
import com.codeless.promotion.mapper.PromotionLinkMapper;
import com.codeless.promotion.service.InitService;
import com.codeless.promotion.service.LinkManageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class LinkManageServiceImpl extends ServiceImpl<PromotionLinkMapper, PromotionLink> implements LinkManageService, InitService {


    /**
     * 服务初始化逻辑
     */
    @Override
    public void init() {
        // 数据量很小不需要进行初始化加载
        ;
    }

}
