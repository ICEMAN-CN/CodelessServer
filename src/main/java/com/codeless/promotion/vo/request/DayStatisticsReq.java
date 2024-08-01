package com.codeless.promotion.vo.request;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "天类型统计请求类")
public class DayStatisticsReq {

    @NotNull
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private DateTime beginTime;

    @NotNull
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private DateTime endTime;

    @ApiModelProperty("统计类型，所有统计数据放到一个表里面，该字段用于区分统计的类型")
    // repeatOpenButtonDay、repeatOpenRefDay、repeatOpenButtonTotal、repeatOpenRefTotal
    // firstOpenButtonDay、firstOpenRefDay、firstOpenButtonTotal、firstOpenRefTotal
    // emailClickTotal、emailClickButtonTotal
    private String statisticsType;

    @ApiModelProperty("业务码，用于标识业务")
    private String businessCode;

}
