package com.suave.newworld.service;

import com.suave.newworld.beans.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suave.newworld.beans.input.UserLoginInput;
import com.suave.newworld.beans.input.UserRegisterInput;
import com.suave.newworld.beans.input.UserUpdateInput;
import com.suave.newworld.beans.output.UserInfoOutput;
import com.suave.newworld.beans.output.UserLoginOutput;
import com.suave.newworld.exception.RespException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
public interface UserService extends IService<User> {
    /**
     * 用户登陆接口
     * @param input
     * @return
     * @throws RespException
     */
    UserLoginOutput login(UserLoginInput input) throws RespException;

    /**
     * 用户注册接口
     * @param input
     * @throws RespException
     */
    void register(UserRegisterInput input) throws RespException;

    /**
     * 通过email获取用户的信息
     * @param email
     * @return
     * @throws RespException
     */
    UserInfoOutput info(String email) throws RespException;

    /**
     * 更新用户
     * @param input
     * @throws RespException
     */
    void update(UserUpdateInput input) throws RespException;
}
