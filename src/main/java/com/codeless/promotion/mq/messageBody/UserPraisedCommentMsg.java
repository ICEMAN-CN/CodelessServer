package com.codeless.promotion.mq.messageBody;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户点赞评论消息
 * @author wangdongyang
 */
@Data
@NoArgsConstructor
public class UserPraisedCommentMsg {

    @ApiModelProperty(value = "点赞人ID")
    private Integer userId;

    @ApiModelProperty(value = "被赞人ID")
    private Integer objectUserId;

    @ApiModelProperty(value = "文章ID")
    private Integer articelId;

    @ApiModelProperty(value = "帖子ID")
    private Integer postId;

    @ApiModelProperty(value = " 点赞评论时间")
    private Long commentPraiseTime  = 0L;

}
