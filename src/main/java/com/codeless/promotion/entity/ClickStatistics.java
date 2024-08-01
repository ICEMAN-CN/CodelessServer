package com.codeless.promotion.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Date;

import static com.codeless.promotion.controller.LinkController.WEB_REF_ID;

/**
 * 点击统计类
 * @author wangdongyang
 */
@Data
public class ClickStatistics  {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("推广链接的唯一标识对应的按钮编号")
    private Integer id;

    @TableField(value = "clid_button")
    @ApiModelProperty("推广链接的唯一标识对应的按钮编号")
    private Integer clIdButton;

    @TableField(value = "refid")
    @ApiModelProperty("推广链接来源标识，用于标记链接来源渠道：邮件、网页")
    private String refId;

    public String getRefSource() {
        if (refId == null) {
            return "";
        }
        return refId.equals(WEB_REF_ID) ? "web" : "email";
    }

    @TableField(exist = false)
    @ApiModelProperty("推广链接来源标识")
    private String refSource;

    @TableField(value = "business_code")
    @ApiModelProperty("业务码，用于标识业务")
    private String businessCode;

    @TableField(value = "pv")
    @ApiModelProperty("pv")
    private Long pv;

    @TableField(value = "uv")
    @ApiModelProperty("uv")
    private Long uv;

    @TableField(value = "customer_email")
    @ApiModelProperty("客户邮箱")
    private String customerEmail;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    // @wdy todo 优化
    @TableField(value = "statistics_type")
    @ApiModelProperty("统计类型，所有统计数据放到一个表里面，该字段用于区分统计的类型")
    // repeatOpenButtonDay、repeatOpenRefDay、repeatOpenButtonTotal、repeatOpenRefTotal
    // firstOpenButtonDay、firstOpenRefDay、firstOpenButtonTotal、firstOpenRefTotal
    // emailClickTotal、emailClickButtonTotal
    private String statisticsType;

}

