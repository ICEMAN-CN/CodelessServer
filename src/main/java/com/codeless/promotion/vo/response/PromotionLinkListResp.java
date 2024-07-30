package com.codeless.promotion.vo.response;

import com.codeless.promotion.entity.PromotionLink;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 推广链接列表的响应类
 *
 */
@Data
public class PromotionLinkListResp {

	@ApiModelProperty(value = "总数")
	private Long totalCount = 0L;

	@ApiModelProperty(value = "总页数")
	private Long totalPage = 0L;

	@ApiModelProperty("列表元素")
	private List<PromotionLink> list;

}
