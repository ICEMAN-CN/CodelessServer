package com.codeless.promotion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codeless.promotion.entity.ClickStatistics;
import com.codeless.promotion.entity.PromotionLink;
import com.codeless.promotion.entity.PromotionLinkClick;
import com.codeless.promotion.enums.PromotionLinkStatus;
import com.codeless.promotion.mapper.ClickStatisticsMapper;
import com.codeless.promotion.mapper.PromotionLinkClickMapper;
import com.codeless.promotion.mapper.PromotionLinkMapper;
import com.codeless.promotion.mq.producer.CommentMsgSender;
import com.codeless.promotion.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class LinkClickServiceImplTest {

    @InjectMocks
    private LinkClickServiceImpl linkClickService;

    @Mock
    private PromotionLinkClickMapper promotionLinkClickMapper;

    @Mock
    private ClickStatisticsMapper clickStatisticsMapper;

    @Mock
    private PromotionLinkMapper promotionLinkMapper;

    @Mock
    private CommentMsgSender commentMsgSender;

    @Before
    public void setUp() {
        // No need to manually initialize clickEvents as it is already done in the LinkClickServiceImpl constructor
    }


    @Test
    public void processPromotionLinkClick_InvalidClid_NoProcessing() {
        PromotionLinkClick click = new PromotionLinkClick();
        click.setClId("invalid");

        when(promotionLinkMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(new ArrayList<>());

        linkClickService.processPromotionLinkClick(click);

        verify(commentMsgSender, never()).sendPromotionLinkClickMsg(any(PromotionLinkClick.class));
    }

    @Test
    public void processPromotionLinkClick_DuplicateClid_NoProcessing() {
        PromotionLinkClick click = new PromotionLinkClick();
        click.setClId("duplicate");

        PromotionLink link1 = new PromotionLink();
        link1.setClIdForButton1("duplicate");

        PromotionLink link2 = new PromotionLink();
        link2.setClIdForButton2("duplicate");

        when(promotionLinkMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(List.of(link1, link2));

        linkClickService.processPromotionLinkClick(click);

        verify(commentMsgSender, never()).sendPromotionLinkClickMsg(any(PromotionLinkClick.class));
    }

    @Test
    public void processPromotionLinkClick_LinkNotActive_NoProcessing() {
        PromotionLinkClick click = new PromotionLinkClick();
        click.setClId("inactive");

        PromotionLink link = new PromotionLink();
        link.setClIdForButton1("inactive");
        link.setStatus(PromotionLinkStatus.DELETED.getValue());

        when(promotionLinkMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(List.of(link));

        linkClickService.processPromotionLinkClick(click);

        verify(commentMsgSender, never()).sendPromotionLinkClickMsg(any(PromotionLinkClick.class));
    }
}
