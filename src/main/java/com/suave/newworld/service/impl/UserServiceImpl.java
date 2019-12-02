package com.suave.newworld.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suave.newworld.beans.db.User;
import com.suave.newworld.beans.input.UserLoginInput;
import com.suave.newworld.beans.output.UserLoginOutput;
import com.suave.newworld.common.RedisKeyConst;
import com.suave.newworld.dao.UserMapper;
import com.suave.newworld.exception.RespError;
import com.suave.newworld.exception.RespException;
import com.suave.newworld.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.newworld.utils.JwtTokenUtil;
import com.suave.newworld.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${token.expiration}")
    private long expiration;

    @Override
    public UserLoginOutput login(UserLoginInput input) throws RespException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        HashMap<String, String> map = new HashMap<>(2);
        map.put("email",input.getEmail());
        map.put("password",SecureUtil.md5(input.getPassword()));
        userQueryWrapper.allEq(map);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user!=null){
            String token = jwtTokenUtil.createToken(user);
            // 查找到用户信息，存入redis
            redisUtil.set(RedisKeyConst.USER_INFO.getKey()+user.getEmail(),user,expiration);
            // 将用户token，存入redis
            redisUtil.set(RedisKeyConst.USER_TOKEN.getKey()+user.getEmail(),token,expiration);
            return new UserLoginOutput().setToken(token);
        } else {
            throw new RespException(RespError.USER_NOT_FOUND);
        }
    }
}
