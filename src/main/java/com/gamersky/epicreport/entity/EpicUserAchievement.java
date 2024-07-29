package com.gamersky.epicreport.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 用户成就表
 * @author wangdongyang
 */
@Data
@TableName(value = "epic_user_achievement")
public class EpicUserAchievement implements Serializable {

    @TableId(value = "id")
    @ApiModelProperty("id")
    private Integer id;

    @TableField(value = "user_id")
    @ApiModelProperty("游戏玩家账号Id")
    private String userId;

    @TableField(value = "game_id")
    @ApiModelProperty("指游戏库里面的id")
    private String gameId;

    @TableField(value = "achievement_id")
    @ApiModelProperty("成就id")
    private String achievementId;

    @TableField(value = "achievement_time")
    @ApiModelProperty("获得成就时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date achievementTime;

    @TableField(value = "crawl_date")
    @ApiModelProperty(value = "采集日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crawlDate;

}
