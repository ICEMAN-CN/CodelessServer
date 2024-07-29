package com.gamersky.epicreport.dto;

import io.swagger.annotations.ApiModelProperty;

public class YearMostFreePageData {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;


    @ApiModelProperty(value = "游戏封面")
    private String gameCover;

    @ApiModelProperty(value = "游戏名称")
    private String gameName;

    @ApiModelProperty(value = "价值 千分制 ￥300")
    private String gamePrice;

}
