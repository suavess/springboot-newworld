package com.suave.newworld.service.impl;

import com.suave.newworld.beans.Users;
import com.suave.newworld.dao.UsersMapper;
import com.suave.newworld.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
