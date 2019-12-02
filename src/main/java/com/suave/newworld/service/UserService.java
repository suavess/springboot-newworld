package com.suave.newworld.service;

import com.suave.newworld.beans.db.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suave.newworld.beans.input.UserLoginInput;
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
}
