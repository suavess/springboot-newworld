package com.suave.newworld.annotation;

import com.suave.newworld.common.Const;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Suave
 * @Date: 2019-11-26 11:55
 * @Desc: 自定义需要token的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Auth {
    String value() default Const.RoleType.USER;
}
