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

    @Override
    public String getMessage() {
        return msg;
    }
}
