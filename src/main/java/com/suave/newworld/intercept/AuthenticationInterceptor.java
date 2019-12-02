package com.suave.newworld.intercept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.suave.newworld.annotation.Auth;
import com.suave.newworld.common.Const;
import com.suave.newworld.common.RedisKeyConst;
import com.suave.newworld.exception.RespError;
import com.suave.newworld.exception.RespException;
import com.suave.newworld.utils.JwtTokenUtil;
import com.suave.newworld.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Suave
 * @Date: 2019-11-26 11:59
 * @Desc: 权限拦截器
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
        if (auth == null) {
            // 没有加注解，不需要权限，直接放行
            return true;
        }
        try {
            // 需要的权限
            String value = auth.value();
            String token = request.getHeader("Authorization");
            if (jwtTokenUtil.isTokenExpired(token)) {
                throw new RespException(RespError.TOKEN_EXPIRED);
            }
            String username = jwtTokenUtil.getEmailFromToken(token);
            // 从缓存中取出和用户相关的信息
             Object json = redisUtil.get(RedisKeyConst.USER_INFO.getKey()+username);
            List<String> roles = JSONUtil.parseArray(json).toList(String.class);
            request.setAttribute("cardNumber", username);
            // 该用户包含ADMIN权限就直接放行
            if (roles.contains(Const.RoleType.ADMIN)) {
                return true;
            }
            if (CollUtil.isNotEmpty(roles) && roles.contains(value)) {
                return true;
            } else {
                throw new RespException(RespError.TOKEN_ERROR);
            }
        } catch (Exception e) {
            if (e instanceof RespException) {
                throw e;
            } else {
                throw new RespException(RespError.TOKEN_ERROR);
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
