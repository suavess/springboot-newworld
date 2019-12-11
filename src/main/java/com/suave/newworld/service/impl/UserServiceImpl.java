package com.suave.newworld.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.suave.newworld.beans.User;
import com.suave.newworld.beans.input.UserLoginInput;
import com.suave.newworld.beans.input.UserRegisterInput;
import com.suave.newworld.beans.input.UserUpdateInput;
import com.suave.newworld.beans.output.UserInfoOutput;
import com.suave.newworld.beans.output.UserLoginOutput;
import com.suave.newworld.common.Const;
import com.suave.newworld.common.RedisKeyConst;
import com.suave.newworld.dao.UserMapper;
import com.suave.newworld.exception.RespError;
import com.suave.newworld.exception.RespException;
import com.suave.newworld.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.newworld.utils.JwtTokenUtil;
import com.suave.newworld.utils.RedisUtil;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * 服务实现类
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

    /**
     * 用户登录方法
     *
     * @param input
     * @return
     * @throws RespException
     */
    @Override
    public UserLoginOutput login(UserLoginInput input) throws RespException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        HashMap<String, String> map = new HashMap<>(2);
        map.put("email", input.getEmail());
        map.put("password", SecureUtil.md5(input.getPassword()));
        userQueryWrapper.allEq(map);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user != null) {
            // 生成token
            String token = jwtTokenUtil.createToken(user);
            // 查找到用户信息，存入redis
            redisUtil.set(RedisKeyConst.USER_INFO.getKey() + user.getEmail(), user, expiration);
            // 将用户token，存入redis
            redisUtil.set(RedisKeyConst.USER_TOKEN.getKey() + user.getEmail(), token, expiration);
            return new UserLoginOutput().setToken(token);
        } else {
            throw new RespException(RespError.USER_NOT_FOUND);
        }
    }

    /**
     * 用户注册方法
     *
     * @param input
     * @throws RespException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void register(UserRegisterInput input) throws RespException {
        if (input.getUsername().length() < 6) {
            throw new RespException(RespError.CUSTOM_ERROR, "用户名不能小于6位");
        }
        if (input.getUsername().length() > 15) {
            throw new RespException(RespError.CUSTOM_ERROR, "用户名不能大于15位");
        }
        final String emailRegex = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        if (!ReUtil.isMatch(emailRegex, input.getEmail())) {
            throw new RespException(RespError.CUSTOM_ERROR, "邮箱格式不正确");
        }
        if (input.getPassword().length() < 6) {
            throw new RespException(RespError.CUSTOM_ERROR, "密码不能小于6位");
        }
        final String pwRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (!ReUtil.isMatch(pwRegex, input.getPassword())) {
            throw new RespException(RespError.CUSTOM_ERROR, "密码必须包含字母和数字");
        }
        User user = new User();
        user.setUsername(input.getUsername())
                .setEmail(input.getEmail())
                .setPassword(SecureUtil.md5(input.getPassword()))
                .setRole(Const.RoleType.USER);
        userMapper.insert(user);
    }

    /**
     * 通过email获取用户信息
     *
     * @param email
     * @return
     * @throws RespException
     */
    @Override
    public UserInfoOutput info(String email) throws RespException {
        UserInfoOutput output = new UserInfoOutput();
        User info = null;
        info = (User) redisUtil.get(RedisKeyConst.USER_INFO.getKey() + email);
        // redis中有时直接返回
        if (info != null) {
            BeanUtil.copyProperties(info, output);
            return output;
        }
        // 没有从mysql中查
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        info = userMapper.selectOne(queryWrapper);
        // 并存入redis
        redisUtil.set(RedisKeyConst.USER_INFO.getKey() + email, info, expiration);
        BeanUtil.copyProperties(info, output);
        return output;
    }

    /**
     * 用户修改信息
     *
     * @param input
     * @throws RespException
     */
    @Override
    @Transactional(rollbackFor = {RespException.class})
    public void update(UserUpdateInput input) throws RespException {
        User user = new User();
        BeanUtil.copyProperties(input, user);
        QueryWrapper<User> updateWrapper = new QueryWrapper<>();
        updateWrapper.select("email", input.getEmail());
        //更新数据库中用户信息参数
        userMapper.update(user, updateWrapper);
        //同时更新redis中的设置
        redisUtil.set(RedisKeyConst.USER_INFO.getKey() + input.getEmail(), input);
    }

    /**
     * 用户退出登录方法
     *
     * @param email
     * @throws RespException
     */
    @Override
    public void logout(String email) throws RespException {
        // 清除redis中的token和个人信息
        redisUtil.del(RedisKeyConst.USER_TOKEN + email);
        redisUtil.del(RedisKeyConst.USER_INFO + email);
    }
}
