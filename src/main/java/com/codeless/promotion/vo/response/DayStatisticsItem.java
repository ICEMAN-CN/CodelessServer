package com.codeless.promotion.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.codeless.promotion.entity.ClickStatistics;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.codeless.promotion.controller.LinkController.WEB_REF_ID;


@Data
@ApiModel(value = "天类型统计单元数据响应类")
public class DayStatisticsItem {

    @ApiModelProperty("业务码，用于标识业务")
    private String businessCode;

    @ApiModelProperty("emailButton1Pv")
    private Long emailButton1Pv = 0L;

    @ApiModelProperty("emailButton2Pv")
    private Long emailButton2Pv = 0L;

    @ApiModelProperty("emailButton1Uv")
    private Long emailButton1Uv = 0L;

    @ApiModelProperty("emailButton2Uv")
    private Long emailButton2Uv = 0L;

    @ApiModelProperty("webButton1Pv")
    private Long webButton1Pv = 0L;

    @ApiModelProperty("webButton2Pv")
    private Long webButton2Pv = 0L;

    @ApiModelProperty("webButton1Uv")
    private Long webButton1Uv = 0L;

    @ApiModelProperty("webButton2Uv")
    private Long webButton2Uv = 0L;

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("统计类型，所有统计数据放到一个表里面，该字段用于区分统计的类型")
    private String statisticsType;

    @ApiModelProperty("推广链接来源标识，用于标记链接来源渠道：邮件、网页")
    private String refId;

    public String getRefSource() {
        if (refId == null) {
            return "";
        }
        return refId.equals(WEB_REF_ID) ? "web" : "email";
    }

    @ApiModelProperty("推广链接来源标识")
    private String refSource;

    @ApiModelProperty("emailPv")
    private Long emailPv = 0L;

    @ApiModelProperty("emailUv")
    private Long emailUv = 0L;

    @ApiModelProperty("webPv")
    private Long webPv = 0L;

    @ApiModelProperty("webUv")
    private Long webUv = 0L;
}
