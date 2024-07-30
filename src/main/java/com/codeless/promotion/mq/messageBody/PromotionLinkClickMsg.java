package com.codeless.promotion.mq.messageBody;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户消息定义
 * @author wangdongyang
 */
@Data
public class PromotionLinkClickMsg {

    @ApiModelProperty("客户邮件")
    private String customerEmail;

    @ApiModelProperty("匿名客户ID，用于web端的点击采集")
    private Long anonymousCustomerId;

    @ApiModelProperty("推广链接的唯一标识")
    private String clId;

    @ApiModelProperty("推广链接来源标识，用于标记链接来源渠道：邮件、网页")
    private String refId;

    @ApiModelProperty("业务码，用于标识业务")
    private String businessCode;

    @ApiModelProperty("客户IP地址")
    private String ipAddress;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
