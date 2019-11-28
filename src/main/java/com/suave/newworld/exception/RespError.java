package com.suave.newworld.exception;

/**
 * @Author: Suave
 * @Date: 2019-11-26 13:30
 * @Desc:
 */
public enum RespError {
    TOKEN_ERROR(401, "非法Token"),
    TOKEN_EXPIRED(402, "Token过期");
    private int code;
    private String msg;

    RespError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
