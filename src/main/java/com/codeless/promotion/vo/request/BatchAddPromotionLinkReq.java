package com.codeless.promotion.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wangdongyang
 *
 */
@Data
@ApiModel("批量增加推广链接的请求类")
public class BatchAddPromotionLinkReq {

	@ApiModelProperty("业务码，用于标识业务")
	private String businessCode;

	public Boolean isValid() {
		return businessCode != null && !"".equals(businessCode);
	}

}
