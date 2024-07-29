package com.gamersky.epicreport.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author iceman
 */
@Data
@ApiModel(value = "白嫖游戏时长数据")
public class FreeGameTimePageData {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "小时数 千分制 543,254h")
    private String playHour;

    // todo 基于现有数据提供给产品，划分不同坐标范围
    @ApiModelProperty(value = "柱状图横坐标 ['￥0-500','￥500-1000','￥1000+']")
    private List<String> xData;

    @ApiModelProperty(value = "柱状图纵坐标 [10,10,10]")
    private List<String> yData;

    @ApiModelProperty(value = "个性化文案")
    private String text;

    @ApiModelProperty(value = "获得称号")
    private String name;

}
