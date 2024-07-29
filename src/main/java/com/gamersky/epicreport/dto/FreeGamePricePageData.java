package com.gamersky.epicreport.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wangdongyang
 */
@Data
@ApiModel(value = "白嫖游戏价值数据")
public class FreeGamePricePageData {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "游戏数")
    private Integer gameNumber;

    @ApiModelProperty(value = "价格 千分制 46,514元")
    private String price;

    @ApiModelProperty(value = "吃灰游戏数")
    private Integer playLessGame;

    // todo 基于现有数据提供给产品，划分不同坐标范围
    @ApiModelProperty(value = "柱状图横坐标 ['￥0-500','￥500-1000','￥1000+']")
    private List<String> xData;

    @ApiModelProperty(value = "柱状图纵坐标 [10,10,10]")
    private List<Integer> yData;

    @ApiModelProperty(value = "个性化文案")
    private String text;

    @ApiModelProperty(value = "获得称号")
    private String name;


}
