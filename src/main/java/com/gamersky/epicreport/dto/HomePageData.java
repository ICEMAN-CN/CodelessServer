package com.gamersky.epicreport.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : wangdongyang
 */
@Data
@ApiModel(value = "年报首页数据")
public class HomePageData {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "epic用户头像")
    private String portrait;

    @ApiModelProperty(value = "epic用户名")
    private String epicUserName;

    @ApiModelProperty(value = "账号价值 千分制 432,523元")
    private String numberPrice;

    @ApiModelProperty(value = "游玩时长 千分制 5,000h")
    private String allPlayTime;

    @ApiModelProperty(value = "游戏数量")
    private Integer gameNumber;

    @ApiModelProperty(value = "年度总结-白嫖游玩时长 千分制 1,000h")
    private String yearReceiveGameTime;

    @ApiModelProperty(value = "年度总结-白嫖游戏数量")
    private Integer yearReceiveGameNumber;

    @ApiModelProperty(value = "年度总结-白嫖游戏价值 千分制41,244元")
    private String yearReceiveGamePrice;

}
