package com.gamersky.epicreport.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 价格走势
*/
@Data
@TableName(value = "epic_game_library_history_price")
public class EpicGameLibraryHistoryPrice implements Serializable {

    @TableId(value = "id")
    @ApiModelProperty("游戏Id")
    private Integer id;

    @TableField(value = "game_id")
    @ApiModelProperty("游戏id 指游戏的 id  不是epic的game_id")
    private String gameId;

    @TableField(value = "game_currency")
    @ApiModelProperty("货币")
    private String gameCurrency;

    @TableField(value = "game_original_price")
    @ApiModelProperty("原始价格 单位分")
    private Integer gameOriginalPrice;

    @TableField(value = "game_discount_price")
    @ApiModelProperty("折扣价格 单位分")
    private Integer gameDiscountPrice;

    @TableField(value = "game_end_date")
    @ApiModelProperty(value = "折扣结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gameEndDate;

    @TableField(value = "game_tags", typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty("标签 用来判断是什么活动")
    private Object gameTags;

    @TableField(value = "crawl_date")
    @ApiModelProperty(value = "采集日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crawlDate;

}
