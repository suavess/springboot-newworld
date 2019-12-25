package com.suave.newworld.service;

import com.suave.newworld.beans.input.UserLoginInput;
import com.suave.newworld.beans.output.AdminCountOutput;
import com.suave.newworld.beans.output.UserInfoOutput;
import com.suave.newworld.beans.output.UserLoginOutput;
import com.suave.newworld.exception.RespException;

/**
 * @author: Suave
 * @date: 2019-12-24 13:48
 */
public interface AdminService {
    /**
     * 管理员登陆接口
     *
     * @param input
     * @return
     * @throws RespException
     */
    UserLoginOutput login(UserLoginInput input) throws RespException;

    /**
     * 管理员退出登录接口
     *
     * @param email
     * @throws RespException
     */
    void logout(String email) throws RespException;

    /**
     * 通过email获取用户的信息
     *
     * @param email
     * @return
     * @throws RespException
     */
    UserInfoOutput info(String email) throws RespException;

    /**
     * 返回管理端首页的三个数据
     * @return
     * @throws RespException
     */
    AdminCountOutput countAll() throws RespException;
}
