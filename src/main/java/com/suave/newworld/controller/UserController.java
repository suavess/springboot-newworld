package com.suave.newworld.controller;


import com.suave.newworld.annotation.Auth;
import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.input.UserLoginInput;
import com.suave.newworld.beans.input.UserRegisterInput;
import com.suave.newworld.beans.input.UserUpdateInput;
import com.suave.newworld.beans.output.UserLoginOutput;
import com.suave.newworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * "/user"(POST)用户注册方法
     *
     * @param input
     * @return
     */
    @PostMapping("")
    public RespObj register(@RequestBody UserRegisterInput input) {
        userService.register(input);
        return RespObj.success();
    }

    /**
     * "/user"(GET)获取当前用户的信息
     *
     * @return
     */
    @Auth
    @GetMapping("")
    public RespObj info(HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        return RespObj.success(userService.info(email));
    }

    /**
     * "/user"(PUT)更新用户信息
     *
     * @param input
     * @return
     */
    @Auth
    @PutMapping("")
    public RespObj update(@RequestBody UserUpdateInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        userService.update(input,email);
        return RespObj.success();
    }

    /**
     * "/user"(DELETE)用户退出登录
     *
     * @param request
     * @return
     */
    @Auth
    @DeleteMapping("")
    public RespObj logout(HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        userService.logout(email);
        return RespObj.success();
    }

    /**
     * "/user/login"(POST)用户登录方法
     *
     * @param input
     * @return
     */
    @PostMapping("login")
    public RespObj<UserLoginOutput> login(@RequestBody UserLoginInput input) {
        return RespObj.success(userService.login(input));
    }


}
