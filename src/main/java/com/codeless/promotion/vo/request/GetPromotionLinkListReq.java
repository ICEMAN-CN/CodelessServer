package com.codeless.promotion.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wangdongyang
 *
 */
@Data
@ApiModel("获取推广链接列表的请求类")
public class GetPromotionLinkListReq {

	@ApiModelProperty("业务码，用于标识业务")
	private String businessCode;

	@ApiModelProperty(value = "要获取的分页序号，起始值为：0")
	private Integer pageIndex = 0;

	@ApiModelProperty(value = "要获取的分页元素数量，默认为：20")
	private Integer pageSize = 20;
	public Boolean isValid() {
        return this.pageIndex >= 0 && this.pageSize > 0;
    }


}
