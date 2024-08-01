package com.codeless.promotion.vo.response;

import com.codeless.promotion.entity.ClickStatistics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@ApiModel(value = "天类型统计响应类")
public class DayStatisticsResp {

    @ApiModelProperty("数据列表")
    List<DayStatisticsItem> list = new ArrayList<>();

}
