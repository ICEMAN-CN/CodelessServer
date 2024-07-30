package com.codeless.promotion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeless.promotion.entity.PromotionLinkClick;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PromotionLinkClickMapper extends BaseMapper<PromotionLinkClick> {

    void insertBatchClickEvents(@Param("list") List<PromotionLinkClick> list);

}
