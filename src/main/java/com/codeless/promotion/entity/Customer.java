package com.codeless.promotion.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.codeless.promotion.enums.CustomerStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static com.codeless.promotion.enums.CustomerStatus.NORMAL;

/**
* 客户基本信息
 * @author wangdongyang
 */
@Data
@TableName(value = "customer")
public class Customer implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("ID")
    private Long id;

    @TableField(value = "email")
    @ApiModelProperty("邮箱地址")
    private String email;

    /**
     * @see CustomerStatus
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "客户状态")
    private Integer status = NORMAL.getValue();

    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
