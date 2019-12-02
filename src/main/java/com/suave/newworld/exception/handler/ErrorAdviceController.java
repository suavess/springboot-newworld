package com.suave.newworld.exception.handler;

import com.suave.newworld.beans.RespObj;
import com.suave.newworld.exception.RespException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Suave
 * 异常拦截器
 */
@Slf4j
@CrossOrigin
@RestController
@RestControllerAdvice
public class ErrorAdviceController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public ErrorAdviceController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public RespObj error(HttpServletRequest request) {
        ServletWebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        return RespObj.error().setMsg((String) attr.get("message")).setCode((Integer) attr.get("status"));
    }

    @ExceptionHandler(Exception.class)
    public RespObj exception(Exception e) {
        log.error(e.getMessage(), e);
        return RespObj.error(e);
    }

    @ExceptionHandler(RespException.class)
    public RespObj respException(RespException e) {
        log.error(e.getMsg(), e);
        return RespObj.failure(e);
    }

    /**
     * @Valid 注解抛出异常的时候使用的注解
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespObj verException(MethodArgumentNotValidException e){
        return RespObj.failure(e);
    }
}
