package com.codeless.promotion.mq.messageBody;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户消息定义
 * @author wangdongyang
 */
@Data
public class UserDataMsg {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

}
