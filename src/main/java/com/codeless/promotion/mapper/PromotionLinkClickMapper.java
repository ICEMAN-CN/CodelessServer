package com.codeless.promotion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeless.promotion.entity.ClickStatistics;
import com.codeless.promotion.entity.PromotionLinkClick;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PromotionLinkClickMapper extends BaseMapper<PromotionLinkClick> {

    void insertBatchClickEvents(@Param("list") List<PromotionLinkClick> list);

    List<ClickStatistics> repeatOpenButtonDay(@Param("begin")String begin, @Param("end") String end);

    List<ClickStatistics> repeatOpenRefDay(@Param("begin")String begin, @Param("end") String end);

    List<ClickStatistics> repeatOpenButtonTotal();

    List<ClickStatistics> repeatOpenRefTotal();

    List<ClickStatistics> firstOpenButtonDay(@Param("begin")String begin, @Param("end") String end);

    List<ClickStatistics> firstOpenRefDay(@Param("begin")String begin, @Param("end") String end);

    List<ClickStatistics> firstOpenButtonTotal();

    List<ClickStatistics> firstOpenRefTotal();

    List<ClickStatistics> emailClickTotal();

    List<ClickStatistics> emailClickButtonTotal();



}
