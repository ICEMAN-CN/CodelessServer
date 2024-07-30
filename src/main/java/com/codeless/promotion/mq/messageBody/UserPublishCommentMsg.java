package com.codeless.promotion.mq.messageBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("用户发布评论消息")
public class UserPublishCommentMsg {

    @ApiModelProperty(value = "用户ID")
    Integer userId;

    @ApiModelProperty(value = "发布时间")
    Long commentPublishTime ;

    @ApiModelProperty(value = "文章ID")
    Integer articleId;

    @ApiModelProperty(value = "帖子ID")
    Integer postId;

}