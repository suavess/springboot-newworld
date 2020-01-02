package com.suave.newworld.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.User;
import com.suave.newworld.beans.input.AdminUserInput;
import com.suave.newworld.common.Const;
import com.suave.newworld.dao.UserMapper;
import com.suave.newworld.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Suave
 * @date 2019-12-27 10:30
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<User> list(AdminUserInput input) {
        String username = input.getUsername();
        String email = input.getEmail();
        String bio = input.getBio();
        // 查询多少条数据
        Integer limit = input.getSize();
        // 从哪里开始查询
        Integer offset = (input.getPage() - 1) * limit;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StrUtil.isEmpty(username)){
            wrapper.like("username",username);
        }
        if (!StrUtil.isEmpty(email)){
            wrapper.like("email",email);
        }
        if (!StrUtil.isEmpty(bio)){
            wrapper.like("bio",bio);
        }
        wrapper.eq("role", Const.RoleType.USER);
        Integer count = userMapper.selectCount(wrapper);
        wrapper.last(String.format("limit %s,%s",offset,limit));
        List<User> users = userMapper.selectList(wrapper);
        Page<User> output = new Page<>();
        output.setTotal(count)
                .setSize(input.getSize())
                .setPage(input.getPage())
                .setRows(users);
        return output;
    }
}
