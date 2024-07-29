package com.gamersky.epicreport.exception;

import com.gamersky.epicreport.enums.GlobalError;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务逻辑异常 Exception
 * @author wangdongyang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class ServiceException extends RuntimeException {

    /**
     * 业务错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public ServiceException(GlobalError error)
    {
        this.message = error.getDesc();
        this.code = error.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public ServiceException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

}
