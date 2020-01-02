package com.suave.newworld.service;

import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.User;
import com.suave.newworld.beans.input.AdminUserInput;

/**
 * @author Suave
 * @date 2019-12-27 10:30
 */
public interface AdminUserService {
    /**
     * 管理端获取用户列表
     *
     * @param input
     * @return
     */
    Page<User> list(AdminUserInput input);
}
