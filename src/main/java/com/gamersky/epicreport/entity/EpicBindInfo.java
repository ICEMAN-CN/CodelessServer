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
* epic绑定关系表
 * @author wangdongyang
*/
@Data
@TableName(value = "epic_bind_info")
public class EpicBindInfo implements Serializable {

    @TableId(value = "gamersky_id")
    @ApiModelProperty("游民id")
    private Integer gamerskyId;

    @TableField(value = "epic_id")
    @ApiModelProperty("epicId")
    private String epicId;

    @TableField(value = "success")
    @ApiModelProperty("是否成功")
    private Integer success;

    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "try_bind_time")
    @ApiModelProperty(value = "尝试绑定时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tryBindTime;

    @TableField(value = "bind_time")
    @ApiModelProperty(value = "绑定时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bindTime;

    @TableField(value = "try_update_time")
    @ApiModelProperty(value = "最后一次尝试刷新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tryUpdateTime;

    @TableField(value = "update_time")
    @ApiModelProperty(value = "最后一次尝试刷新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(value = "unbind_time")
    @ApiModelProperty(value = "最后一次解绑时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date unbindTime;

    @TableField(value = "is_reward")
    @ApiModelProperty("是否发放过绑定奖励")
    private Integer isReward;

}
