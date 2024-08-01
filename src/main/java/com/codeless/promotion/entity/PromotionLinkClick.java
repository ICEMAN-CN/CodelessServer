package com.codeless.promotion.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 推广链接点击信息
 * @author wangdongyang
 */
@Data
@TableName(value = "promotion_link_click")
public class PromotionLinkClick implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("ID")
    private Long id;

    @TableField(value = "customer_email")
    @ApiModelProperty("客户邮箱")
    private String customerEmail;

//    @TableField(value = "anonymous_customer_id")
//    @ApiModelProperty("匿名客户ID，用于web端的点击采集")
//    private Long anonymousCustomerId;

    @TableField(value = "clid")
    @ApiModelProperty("推广链接的唯一标识")
    private String clId;

    @TableField(value = "clid_button")
    @ApiModelProperty("推广链接的唯一标识对应的按钮编号")
    private Integer clIdButton;

    @TableField(value = "refid")
    @ApiModelProperty("推广链接来源标识，用于标记链接来源渠道：邮件、网页")
    private String refId;

    @TableField(value = "business_code")
    @ApiModelProperty("业务码，用于标识业务")
    private String businessCode;

    // @wdy todo 后续需要分析不同地域用户行为分布，可以扩充该字段
//    @TableField(value = "ip_address_id")
//    @ApiModelProperty("客户IP地址ID")
//    private Long ipAddressId;

    @TableField(value = "ip_address")
    @ApiModelProperty("客户IP地址")
    private String ipAddress;

    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
