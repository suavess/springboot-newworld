package com.suave.newworld.controller;


import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.db.User;
import com.suave.newworld.beans.input.UserLoginInput;
import com.suave.newworld.beans.output.UserLoginOutput;
import com.suave.newworld.service.UserService;
import com.suave.newworld.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
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

    @PostMapping("login")
    public RespObj<UserLoginOutput> login(UserLoginInput input){
        return RespObj.success(userService.login(input));
    }

}
