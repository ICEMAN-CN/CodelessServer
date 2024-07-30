package com.codeless.promotion.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wangdongyang
 *
 */
@Data
@ApiModel("生成邮件和web推广url的响应类")
public class GenerateEmailAndWebUrlResp {

	@ApiModelProperty("邮件推广文本")
	private String emailText;

	@ApiModelProperty("网页Url")
	private String webUrl;

}
