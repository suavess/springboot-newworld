package com.suave.newworld.utils;

import com.suave.newworld.beans.db.User;
import com.suave.newworld.exception.RespError;
import com.suave.newworld.exception.RespException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * @Author: Suave
 * @Date: 2019-11-26 12:06
 * @Desc: Jwt工具类
 */
@Component
public class JwtTokenUtil {
    private static final String ISS = "suave";
    public static final String TOKEN_HEADER = "Authorization";
    /**
     * Token过期时间
     */
    public static final long EXPIRATION = 3600L;
    /**
     * Token秘钥
     */
    private static final String SECRET = "ipyq";

    /**
     * 生成Token，存入username
     * @param user
     * @return
     */
    public static String createToken(User user) {
        //可以将基本不重要的对象信息放到claims中，此处信息不多,见简单直接放到配置内
        HashMap<String,Object> claims = new HashMap<String,Object>(1);
        claims.put("username",user.getUsername());
        //id是重要信息，进行加密下
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                // 这里要早set一点，放到后面会覆盖别的字段
                .setClaims(claims)
                .setIssuer(ISS)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
    }

    /**
     * 获取token是否过期
     */
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 根据token获取cardNumber
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 获取token的过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 解析JWT
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
