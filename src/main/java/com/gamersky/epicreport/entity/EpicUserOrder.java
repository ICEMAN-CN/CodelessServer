package com.gamersky.epicreport.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 用户订单表
 * @author wangdongyang
 */
@Data
@TableName(value = "epic_user_order")
public class EpicUserOrder implements Serializable {

    @TableId(value = "id")
    @ApiModelProperty("id")
    private Integer id;
    @TableField(value = "user_id")
    @ApiModelProperty("游戏玩家id")
    private String userId;

    @TableField(value = "order_id")
    @ApiModelProperty("订单id")
    private String orderId;
    @TableField(value = "order_desc")
    @ApiModelProperty("订单描述")
    private String orderDesc;

    @TableField(value = "order_items", typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty("订单项目")
    private Object orderItems;

    @TableField(value = "order_currency")
    @ApiModelProperty("订单的付款货币")
    private String orderCurrency;

    @TableField(value = "order_price")
    @ApiModelProperty("订单的价格")
    private Integer orderPrice;

    @TableField(value = "order_status")
    @ApiModelProperty("订单的状态")
    private String orderStatus;

    @TableField(value = "crawl_date")
    @ApiModelProperty(value = "采集日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crawlDate;

}
