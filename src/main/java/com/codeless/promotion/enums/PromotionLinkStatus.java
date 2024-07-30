package com.codeless.promotion.enums;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("推广链接状态")
public enum PromotionLinkStatus {

    NORMAL(0, "正常"),

    DELETED(1, "已删除"),

    EXPIRED(3, "已过期")

    ;
    private final Integer value;

    private final String desc;

    public static Integer getEnumValue(String index) {
        for (PromotionLinkStatus userStateEnum : PromotionLinkStatus.values()) {
            if (userStateEnum.getDesc().equals(index)) {
                return userStateEnum.getValue();
            }
        }
        return null;
    }

}
