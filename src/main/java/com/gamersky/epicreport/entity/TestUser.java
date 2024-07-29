package com.gamersky.epicreport.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
* 测试用户信息表
 * @author wangdongyang
 */
@Data
@TableName(value = "epic_user")
@Accessors(chain = true)
public class TestUser implements Serializable {


    @TableId(value = "id")
    @ApiModelProperty("id")
    private Integer userId;

    @TableField(value = "user_name")
    @ApiModelProperty("昵称")
    private String userName;

    @TableField(value = "user_avatar")
    @ApiModelProperty("头像")
    private String userAvatar;

    @TableField(value = "crawl_date")
    @ApiModelProperty("采集时间")
    private Date crawlDate;


    public boolean isValid() {
        return true;
    }

}
