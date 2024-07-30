package com.codeless.promotion.vo.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wangdongyang
 *
 */
@Data
@ApiModel("生成邮件和web推广url的请求类")
public class GenerateEmailAndWebUrlReq {

	@ApiModelProperty("推广链接的唯一标识，按钮1的ID")
	private String clIdForButton1;

	@ApiModelProperty("推广链接的唯一标识，按钮2的ID")
	private String clIdForButton2;

	public boolean isValid() {
		return clIdForButton1 != null && !"".equals(clIdForButton1) && clIdForButton2 != null && !"".equals(clIdForButton2);
	}

}
