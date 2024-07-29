package com.gamersky.epicreport.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码与返回信息
 *
 * @author wangshijie
 * @version : 1.0
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 接口成功码
    SUCCESS(0, "处理成功"),
    // 接口失败码
    ERROR(-1, "系统异常");

    private final Integer code;

    private final String message;
}
