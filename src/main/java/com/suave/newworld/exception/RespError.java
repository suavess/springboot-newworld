package com.suave.newworld.exception;

/**
 * @Author: Suave
 * @Date: 2019-11-26 13:30
 * @Desc:
 */
public enum RespError {
    TOKEN_ERROR(401, "非法Token！"),
    TOKEN_EXPIRED(402, "登录过期！"),
    TOKEN_OFFSITE(403,"账号在其他地方登录，如不是本人登录请及时修改密码！"),
    USER_NOT_FOUND(405, "用户名或密码错误"),
    CUSTOM_ERROR(499,"自定义异常");
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
