package com.gamersky.epicreport.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 用户信息表
 * @author wangdongyang
 */
@Data
@TableName(value = "epic_user")
public class EpicUser implements Serializable {


    @TableId(value = "id")
    @ApiModelProperty("id")
    private String userId;

    @TableField(value = "user_name")
    @ApiModelProperty("昵称")
    private String userName;

    @TableField(value = "user_avatar")
    @ApiModelProperty("头像")
    private String userAvatar;

    @TableField(value = "crawl_date")
    @ApiModelProperty("采集时间")
    private Date crawlDate;


}
