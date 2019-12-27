package com.suave.newworld.controller.admin;

import cn.hutool.crypto.SecureUtil;
import com.suave.newworld.annotation.Auth;
import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.User;
import com.suave.newworld.beans.input.AdminUserInput;
import com.suave.newworld.service.AdminUserService;
import com.suave.newworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理端对用户管理的操作
 *
 * @author: Suave
 * @date: 2019-12-27 10:22
 */
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private UserService userService;

    /**
     * "/admin/users"(GET)返回用户列表，分页
     *
     * @param input
     * @return
     */
    @Auth("ADMIN")
    @GetMapping("")
    public RespObj<Page<User>> list(AdminUserInput input) {
        return RespObj.success(adminUserService.list(input));
    }

    /**
     * "/admin/users"(Delete)通过id删除用户
     *
     * @param input
     * @return
     */
    @Auth("ADMIN")
    @DeleteMapping("")
    public RespObj del(@RequestBody Map<String,Integer> input) {
        userService.removeById(input.get("id"));
        return RespObj.success();
    }

    /**
     * "/admin/users"(PUT)更新用户
     *
     * @param input
     * @return
     */
    @Auth("ADMIN")
    @PutMapping("")
    public RespObj update(@RequestBody User input) {
        userService.updateById(input);
        return RespObj.success();
    }

    /**
     * "/admin/users/reset"(POST)重置用户密码为123456
     *
     * @param input
     * @return
     */
    @Auth("ADMIN")
    @PutMapping("reset")
    public RespObj reset(@RequestBody User input) {
        input.setPassword(SecureUtil.md5("123456"));
        userService.updateById(input);
        return RespObj.success();
    }
}
