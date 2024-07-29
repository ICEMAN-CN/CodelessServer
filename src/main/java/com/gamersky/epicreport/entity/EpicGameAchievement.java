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
* 游戏成就
 * @author wangdongyang
 */
@Data
@TableName(value = "epic_game_achievement")
public class EpicGameAchievement implements Serializable {

    
    @TableId(value = "id")
    @ApiModelProperty("ID")
    private Integer id;

    @TableField(value = "game_id")
    @ApiModelProperty("游戏id")
    private String gameId;

    @TableField(value = "achievement_id")
    @ApiModelProperty("成就id")
    private String achievementId;

    @TableField(value = "achievement_name")
    @ApiModelProperty("成就名称")
    private String achievementName;

    @TableField(value = "achievement_icon")
    @ApiModelProperty("成就图标")
    private String achievementIcon;

    @TableField(value = "achievement_desc")
    @ApiModelProperty("成就描述")
    private String achievementDesc;

    @TableField(value = "crawl_date")
    @ApiModelProperty(value = "采集时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crawlDate;
}
