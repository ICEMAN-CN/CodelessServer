package com.gamersky.epicreport.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统错误码
 *
 * @author : wangdongyang
 */
@Getter
@AllArgsConstructor
public enum GlobalError {

    /**
     * 系统错误码定义 0-999
     */
    BAD_REQUEST(400, "请求参数不正确"),
    UNAUTHORIZED(401, "账号未登录"),
    FORBIDDEN(403, "没有操作权限"),
    NOT_FOUND(404, "请求未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不正确"),
    INTERNAL_SERVER_ERROR(500, "系统异常"),

    /**
     * 登录验证模块 1000+
     */
    GET_LOGIN_USER_FAILED(1002, "获取登录用户信息异常"),
    AUTH_TOKEN_EXPIRED(1003, "账号已被登出"),
    AUTH_USER_STATUS_ILLEGAL(1004, "用户状态异常：用户锁定或已删除"),
    AUTH_JWT_INVALID(1005, "toke JWT校验异常"),
    DELETE_AUTH_TOKEN_FAILED(1006, "删除用户Token异常"),

    /**
     * 用户模块 2000+
     */
    USER_USERNAME_EXISTS(2000, "用户账号已经存在"),
    USER_NOT_EXISTS(2001, "用户不存在"),
    USER_NOT_VALID_GAMERSKY_USER(2002, "用户账号不是合法的游民账号，请使用GS账号"),
    OLD_PASSWORD_ERROR(2003, "修改密码失败，旧密码错误"),
    NEW_PASSWORD_REPEAT(2004, "修改密码失败，新密码不能与旧密码相同"),
    USERNAME_CAN_NOT_EDIT(2005, "用户名不可修改"),
    USERNAME_PASSWORD_ERROR(2006, "1.密码必须由字母、数字、特殊符号组成，区分大小写 2.特殊符号包含（. _ ~ ! @ # $ ^ & *） 3.密码长度为8-20位"),
    USER_ROLE_IS_EMPTY(2007, "管理员角色列表不能为空"),
    ONLY_ADMIN_CAN_MODIFY_USER_PSW(2008, "只有超管才可以修改管理员密码"),
    USERMOBILE_MUST_ELEVEN(2009, "手机号必须为11位，且符合正确格式"),

    /**
     * 角色模块 3000+
     */
    ROLE_NOT_EXISTS(3000, "角色不存在"),
    ROLE_NAME_DUPLICATE(3001, "已经存在名为【{}】的角色"),
    ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE(3002, "不能操作类型为系统内置的角色"),

    /**
     * 短信模板 4000+
     */
    SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY(4001, "超过每日短信发送数量"),
    SMS_CODE_SEND_TOO_FAST(4002, "短信发送过于频率");

    private final Integer code;
    private final String desc;
}
