package com.suave.newworld.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.suave.newworld.intercept.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
        // 拦截所有请求
        registry.addInterceptor(AuthenticationInterceptor).addPathPatterns("/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 实例化FastJson消息转换器对象
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        // 实例化FastJson配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 设置字符集为UTF-8
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        // 设置默认时间格式化方式为 yyyy-MM-dd HH:mm:ss
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置FastJson序列化器功能为PrettyFormat，默认做格式化
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 设置FastJson消息转换器的配置对象
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON);
        // 设置支持的媒体类型为application json
        fastJsonHttpMessageConverter.setSupportedMediaTypes(list);
        // 配置消息转换器
        converters.add(fastJsonHttpMessageConverter);
    }
}
