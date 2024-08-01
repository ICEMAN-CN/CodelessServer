package com.codeless.promotion.vo.response;

import com.codeless.promotion.entity.ClickStatistics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@ApiModel(value = "总类型统计响应类")
public class TotalStatisticsResp {

    @ApiModelProperty("数据列表")
    List<ClickStatistics> list = new ArrayList<>();

    @ApiModelProperty("业务码，用于标识业务")
    private String businessCode;

}
