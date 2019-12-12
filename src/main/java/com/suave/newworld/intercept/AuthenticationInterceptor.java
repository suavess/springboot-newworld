package com.suave.newworld.intercept;

import com.suave.newworld.annotation.Auth;
import com.suave.newworld.beans.User;
import com.suave.newworld.common.Const;
import com.suave.newworld.common.RedisKeyConst;
import com.suave.newworld.exception.RespError;
import com.suave.newworld.exception.RespException;
import com.suave.newworld.utils.JwtTokenUtil;
import com.suave.newworld.utils.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Value("${token.header}")
    private String header;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String token = request.getHeader(header);
        String email = null;
        if (token != null) {
            try {
                if (!jwtTokenUtil.isTokenExpired(token)) {
                    email = jwtTokenUtil.getEmailFromToken(token);
                    // 将用户email存入request
                    request.setAttribute("email", email);
                }
            } catch (ExpiredJwtException e) {
                throw new RespException(RespError.TOKEN_EXPIRED);
            }
        }
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
        if (auth == null) {
            // 没有加注解，不需要权限，直接放行
            return true;
        }
        try {
            // 需要的权限
            String value = auth.value();
            if (jwtTokenUtil.isTokenExpired(token)) {
                throw new RespException(RespError.TOKEN_EXPIRED);
            }
            // 从缓存中取出和用户相关的信息
            Object redisToken = redisUtil.get(RedisKeyConst.USER_TOKEN.getKey() + email);
            if (!token.equals(redisToken)) {
                // 如果redis中的token与传过来的token不一致，表示重复登录
                throw new RespException(RespError.TOKEN_OFFSITE);
            }
            // 取出redis中的数据
            Object info = redisUtil.get(RedisKeyConst.USER_INFO.getKey() + email);
            User user = (User) info;
            // 管理员权限允许访问所有接口
            if (Const.RoleType.ADMIN.equals(user.getRole())) {
                return true;
            }
            if (value.equals(user.getRole())) {
                return true;
            } else {
                throw new RespException(RespError.TOKEN_ERROR);
            }
        } catch (Exception e) {
            if (e instanceof RespException) {
                throw new RespException(RespError.TOKEN_ERROR);
            } else if (e instanceof ExpiredJwtException){
                throw new RespException(RespError.TOKEN_EXPIRED);
            } else {
                throw e;
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
