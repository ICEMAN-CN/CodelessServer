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
* 游戏库
 * @author wangdongyang
*/
@Data
@TableName(value = "epic_game_library")
public class EpicGameLibrary implements Serializable {

    @TableId(value = "id")
    @ApiModelProperty("游戏Id")
    private String id;

    @TableField(value = "game_id")
    @ApiModelProperty("epic的game_id为 namespace")
    private String gameId;

    @TableField(value = "game_name")
    @ApiModelProperty("游戏名称")
    private String gameName;

    @TableField(value = "game_category")
    @ApiModelProperty("游戏类别 games/edition/base, bundles/games, editors, addons, games/demo, software/edition/base, " +
            "游戏/版本/基地, 包/游戏, 编辑器, 插件, 游戏/演示, 软件/版本/基地,")
    private String gameCategory;

    @TableField(value = "game_is_dlc")
    @ApiModelProperty("1 为dlc 0为不是dlc")
    private Integer gameIsDlc;

    @TableField(value = "game_store_url")
    @ApiModelProperty("游戏链接")
    private String gameStoreUrl;

    @TableField(value = "game_cover")
    @ApiModelProperty("logo")
    private String gameCover;

    @TableField(value = "game_publish_time")
    @ApiModelProperty(value = "发行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gamePublishTime;

    @TableField(value = "crawl_date")
    @ApiModelProperty(value = "采集日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crawlDate;

}
