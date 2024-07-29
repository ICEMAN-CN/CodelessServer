package com.gamersky.epicreport.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wangdongyang
 */
@Data
@ApiModel(value = "Epic白嫖游戏单")
public class EpicFreeGameListPageData {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "游戏列表")
    private List<FreeGame> gameList;

    @Data
    public static class FreeGame {

        @ApiModelProperty(value = "游戏封面")
        private String gameCover;

        @ApiModelProperty(value = "游戏名称")
        private String gameName;

        @ApiModelProperty(value = "是否已白嫖")
        private Boolean isReceive;

    }

}
