package com.gamersky.epicreport.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangdongyang
 */
@Data
@ApiModel(value = "年度热门游戏")
public class YearHostGamePageData {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "游戏封面")
    private String gameCover;

    @ApiModelProperty(value = "游戏名称")
    private String gameName;

    @ApiModelProperty(value = "有xxxxx名游民也很喜欢它")
    private Integer playNumber;

}
