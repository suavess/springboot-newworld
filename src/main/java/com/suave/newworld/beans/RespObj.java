package com.suave.newworld.beans;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.suave.newworld.exception.RespException;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
public class RespObj<T> {
    // 正常200 业务异常400 系统异常500
    private int code;
    private String msg;
    private T data;

    // 私有构造方法
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

    public static RespObj failure() {
        RespObj respObj = new RespObj();
        respObj.code = 400;
        respObj.msg = "FAILURE";
        return respObj;
    }

    public static RespObj failure(RespException e) {
        RespObj respObj = new RespObj();
        respObj.code = e.getCode();
        respObj.msg = e.getMsg();
        return respObj;
    }

    public static RespObj error() {
        RespObj respObj = new RespObj();
        respObj.code = 500;
        respObj.msg = "ERROR";
        return respObj;
    }

    public static RespObj error(Throwable t) {
        RespObj respObj = new RespObj();
        respObj.code = 500;
        StringBuilder sb = new StringBuilder();
        sb.append(t.getMessage());
        for (Throwable throwable : t.getSuppressed()) {
            sb.append("\n");
            sb.append(throwable.getMessage());
        }
        respObj.msg = sb.toString();
        return respObj;
    }

    public static RespObj failure(MethodArgumentNotValidException e) {
        RespObj respObj = new RespObj();
        respObj.code = 400;
        StringBuilder sb = new StringBuilder();
        List<ObjectError> allErrors =
                e.getBindingResult().getAllErrors();
        if (CollUtil.isNotEmpty(allErrors)) {
            /*sb.append(allErrors.get(0).getDefaultMessage());*/
            for (ObjectError error : allErrors) {
                if (sb.length() > 0){
                    sb.append("/n");
                }
                sb.append(StrUtil.format("字段: {}, {}", Arrays.toString(error.getCodes()),
                        error.getDefaultMessage()));
            }
        }
        respObj.msg = sb.toString();
        return respObj;
    }
}
