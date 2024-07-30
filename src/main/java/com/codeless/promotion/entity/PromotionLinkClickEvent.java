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
@TableName(value = "promotion_link_click_event")
public class PromotionLinkClickEvent implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("ID")
    private Long id;

    @TableField(value = "customer_id")
    @ApiModelProperty("客户标识")
    private Long customerId;

    @TableField(value = "anonymous_customer_id")
    @ApiModelProperty("匿名客户ID")
    private Long anonymousCustomerId;

    @TableField(value = "promotion_link_id")
    @ApiModelProperty("推广链接ID")
    private Long promotionLinkId;

    // @wdy todo 后续需要分析不同地域用户行为分布，可以扩充该字段
    @TableField(value = "ip_address_id")
    @ApiModelProperty("客户IP地址ID")
    private Long ipAddressId;

    @TableField(value = "business_code")
    @ApiModelProperty("业务码，用于标识业务")
    private String businessCode = "abc";

    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
