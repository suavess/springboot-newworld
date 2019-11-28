package com.suave.newworld.configuration;

import com.suave.newworld.intercept.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Suave
 * @Date: 2019-11-26 12:03
 * @Desc:
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    private AuthenticationInterceptor AuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(AuthenticationInterceptor).addPathPatterns("/**");
    }
}
