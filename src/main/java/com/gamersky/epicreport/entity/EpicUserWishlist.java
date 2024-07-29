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
 * 愿望单
 * @author wangdongyang
 */
@Data
@TableName(value = "epic_user_wishlist")
public class EpicUserWishlist implements Serializable {

    @TableId(value = "user_id")
    @ApiModelProperty("id")
    private String userId;

    @TableField(value = "wishlist", typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty("愿望清单-里面存游戏库的id")
    private Object wishlist;

    @TableField(value = "crawl_date")
    @ApiModelProperty(value = "采集日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crawlDate;

}
