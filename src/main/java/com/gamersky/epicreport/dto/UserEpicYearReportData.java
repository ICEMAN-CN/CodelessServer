package com.gamersky.epicreport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;


/**
 * 用户年报首页数据
 *
 * @author : wangdongyang
 */
@Data
@ApiModel(value = "用户年报数据")
public class UserEpicYearReportData {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "生成状态标识 1：生成完成  2：正在生成  3：无法生成")
    private boolean reportStatus;

    @ApiModelProperty(value = "年报首页数据")
    private HomePageData annualReportHome;

    @ApiModelProperty(value = "白嫖游戏价值数据")
    private FreeGamePricePageData receiveGamePricePage;

    @ApiModelProperty(value = "白嫖游戏时长数据")
    private FreeGameTimePageData receiveGameTimePage;

    @ApiModelProperty(value = "今年最赚白嫖")
    private YearMostFreePageData earnReceivePage;

    @ApiModelProperty(value = "年度热门游戏")
    private YearHostGamePageData yearHotGamePage;

    @ApiModelProperty(value = "Epic白嫖游戏单")
    private EpicFreeGameListPageData receiveGameListPage;

    @ApiModelProperty(value = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dataTime;

}
