package com.suave.newworld.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Suave
 * @Date: 2019-11-26 13:28
 * @Desc:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RespException extends RuntimeException {

    private RespError respError;

    private int code;
    private String msg;

    /**
     * 常见异常
     */
    public RespException(RespError respError) {
        super(respError.getMsg());
        this.respError = respError;
        this.code = respError.getCode();
        this.msg = respError.getMsg();
    }

    /**
     * 自定义异常
     */
    public RespException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 半自定义异常
     */
    public RespException(RespError respError, String msg) {
        super(msg);
        this.code = respError.getCode();
        this.msg = msg;
    }

    public RespException(String message, RespError respError) {
        super(message);
        this.respError = respError;
    }

    public RespException(String message, Throwable cause, RespError respError) {
        super(message, cause);
        this.respError = respError;
    }

    public RespException(Throwable cause, RespError respError) {
        super(cause);
        this.respError = respError;
    }

    public RespException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, RespError respError) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.respError = respError;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
