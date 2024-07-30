package com.codeless.promotion.enums;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("客户状态")
public enum CustomerStatus {

    NORMAL(0, "正常"),

    IGNORED(1, "忽略"),

    ;
    private final Integer value;

    private final String desc;

    public static Integer getEnumValue(String index) {
        for (CustomerStatus userStateEnum : CustomerStatus.values()) {
            if (userStateEnum.getDesc().equals(index)) {
                return userStateEnum.getValue();
            }
        }
        return null;
    }

}
