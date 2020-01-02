package com.suave.newworld.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suave.newworld.beans.Articles;
import com.suave.newworld.beans.User;
import com.suave.newworld.beans.input.UserLoginInput;
import com.suave.newworld.beans.output.AdminAnalyzeOutput;
import com.suave.newworld.beans.output.AdminCountOutput;
import com.suave.newworld.beans.output.UserInfoOutput;
import com.suave.newworld.beans.output.UserLoginOutput;
import com.suave.newworld.common.Const;
import com.suave.newworld.common.RedisKeyConst;
import com.suave.newworld.dao.ArticlesMapper;
import com.suave.newworld.dao.TagsMapper;
import com.suave.newworld.dao.UserMapper;
import com.suave.newworld.exception.RespError;
import com.suave.newworld.exception.RespException;
import com.suave.newworld.service.AdminService;
import com.suave.newworld.utils.JwtTokenUtil;
import com.suave.newworld.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Suave
 * @date 2019-12-24 13:48
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticlesMapper articlesMapper;

    @Autowired
    private TagsMapper tagsMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${token.expiration}")
    private long expiration;

    @Override
    public UserLoginOutput login(UserLoginInput input) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", input.getEmail())
                .eq("password", SecureUtil.md5(input.getPassword()))
                .eq("role", Const.RoleType.ADMIN);
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

    @Override
    public UserInfoOutput info(String email) {
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

    @Override
    public void logout(String email) {
        // 清除redis中的token和个人信息
        redisUtil.del(RedisKeyConst.USER_TOKEN + email);
        redisUtil.del(RedisKeyConst.USER_INFO + email);
    }

    @Override
    public AdminCountOutput countAll() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("role", "USER");
        Integer userCount = userMapper.selectCount(userQueryWrapper);
        Integer articleCount = articlesMapper.selectCount(null);
        Integer tagsCount = tagsMapper.selectCount(null);
        return new AdminCountOutput()
                .setUserCount(userCount)
                .setArticleCount(articleCount)
                .setTagsCount(tagsCount);
    }

    @Override
    public AdminAnalyzeOutput analyze() {
        List<String> columns = new ArrayList<>();
        List<Map<String, Object>> rows = new ArrayList<>();
        columns.add("时间");
        columns.add("发布文章");
        AdminAnalyzeOutput output = new AdminAnalyzeOutput();
        DateTime now = DateTime.now();
        for (int i = 7; i > 0; i--) {
            Map<String, Object> map = new HashMap<>();
            DateTime newDate = DateUtil.offsetDay(now, -i);
            map.put("时间",newDate.toString("yyyy-MM-dd"));
            QueryWrapper<Articles> wrapper = new QueryWrapper<>();
            wrapper.apply("date_format(created_at,'%Y-%m-%d') = '"+newDate.toString("yyyy-MM-dd")+"'");
            Integer count = articlesMapper.selectCount(wrapper);
            map.put("发布文章",count);
            rows.add(map);
        }
        output.setColumns(columns).setRows(rows);
        return output;
    }
}
