package com.codeless.promotion.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "总类型统计请求类")
public class TotalStatisticsReq {

    @ApiModelProperty("统计类型，所有统计数据放到一个表里面，该字段用于区分统计的类型")
    // repeatOpenButtonDay、repeatOpenRefDay、repeatOpenButtonTotal、repeatOpenRefTotal
    // firstOpenButtonDay、firstOpenRefDay、firstOpenButtonTotal、firstOpenRefTotal
    // emailClickTotal、emailClickButtonTotal
    private String statisticsType;

    @ApiModelProperty("业务码，用于标识业务")
    private String businessCode;

}
