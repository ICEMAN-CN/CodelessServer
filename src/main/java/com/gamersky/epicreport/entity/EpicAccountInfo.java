package com.gamersky.epicreport.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* epic账号信息表
 * @author wangdongyang
 */
@Data
@TableName(value = "epic_account_info")
public class EpicAccountInfo implements Serializable {

    @TableId(value = "epic_id")
    @ApiModelProperty("epicId")
    private String epicId;

    @TableField(value = "nickname")
    @ApiModelProperty("用户昵称")
    private String nickname;

    @TableField(value = "avatar")
    @ApiModelProperty("头像")
    private String avatar;
    @TableField(value = "total_game_price")
    @ApiModelProperty("游戏总价")
    private Double totalGamePrice;

    @TableField(value = "total_game_library")
    @ApiModelProperty("游戏总数量")
    private Integer totalGameLibrary;

    @TableField(value = "total_game_duration")
    @ApiModelProperty("游戏总时长")
    private Double totalGameDuration;

    @TableField(value = "cookie")
    @ApiModelProperty("cookie")
    private String cookie;

    @TableField(value = "account")
    @ApiModelProperty("epic账号")
    private String account;

    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
