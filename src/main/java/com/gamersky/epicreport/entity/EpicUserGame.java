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
 * 用户游戏表
 */
@Data
@TableName(value = "epic_user_game")
public class EpicUserGame implements Serializable {


    @TableId(value = "id")
    @ApiModelProperty("id")
    private Integer id;

    @TableField(value = "user_id")
    @ApiModelProperty("游戏玩家id")
    private String userId;

    @TableField(value = "game_id")
    @ApiModelProperty("指的是游戏库的game_id")
    private String gameId;

    @TableField(value = "play_time")
    @ApiModelProperty("游戏时长")
    private Integer playTime;

    @TableField(value = "last_play_time")
    @ApiModelProperty("最后游戏时间")
    private Date lastPlayTime;

    @TableField(value = "add_time")
    @ApiModelProperty("游戏添加时间也可以是订单上的时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;

    @TableField(value = "crawl_date")
    @ApiModelProperty("采集时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crawlDate;


    @TableField(value = "achievement_time")
    @ApiModelProperty("获得成就时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date achievementTime;

}
