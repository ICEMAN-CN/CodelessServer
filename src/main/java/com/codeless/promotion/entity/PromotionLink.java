package com.codeless.promotion.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codeless.promotion.enums.PromotionLinkStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 推广链接基本信息
 * @author wangdongyang
 */
@Data
@TableName(value = "promotion_link")
public class PromotionLink implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("ID")
    private Long id;

    @TableField(value = "clid_b1")
    @ApiModelProperty("推广链接的唯一标识，按钮1的ID")
    private String clIdForButton1;

    @TableField(value = "clid_b2")
    @ApiModelProperty("推广链接的唯一标识，按钮2的ID")
    private String clIdForButton2;

    // @wdy todo 先固定为email、web等字符串，后续需要再扩充标识
    @TableField(value = "refid")
    @ApiModelProperty("推广链接来源标识，用于标记链接来源渠道：邮件、网页")
    private String refId;

    @TableField(value = "customer_email")
    @ApiModelProperty("客户邮箱")
    private String customerEmail;

    // @wdy todo 预留字段，固定为abc
    @TableField(value = "business_code")
    @ApiModelProperty("业务码，用于标识业务")
    private String businessCode;

    // @wdy todo 预留字段，用于定时过期等判断
    /**
     * @see PromotionLinkStatus
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "推广链接状态")
    private Integer status = PromotionLinkStatus.NORMAL.getValue();

    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
