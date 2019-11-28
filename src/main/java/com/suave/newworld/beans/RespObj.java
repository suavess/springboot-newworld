package com.suave.newworld.beans;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Suave
 * @Date: 2019-11-26 12:54
 * @Desc: 自定义返回类
 */
@Data
@Accessors(chain = true)
public class RespObj<T> {
    private int code;
    private String msg;
    private T data;

    private RespObj() {
    }

    public static RespObj success() {
        RespObj respObj = new RespObj();
        respObj.code = 200;
        respObj.msg = "SUCCESS";
        return respObj;
    }

    public static <T> RespObj<T> success(T data) {
        RespObj<T> respObj = new RespObj<>();
        respObj.code = 200;
        respObj.msg = "SUCCESS";
        respObj.data = data;
        return respObj;
    }

    public static RespObj error() {
        RespObj respObj = new RespObj();
        respObj.code = 500;
        respObj.msg = "ERROR";
        return respObj;
    }
}
