package com.suave.newworld.controller.admin;

import com.suave.newworld.annotation.Auth;
import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.input.UserLoginInput;
import com.suave.newworld.beans.output.AdminCountOutput;
import com.suave.newworld.beans.output.UserLoginOutput;
import com.suave.newworld.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理相关，首页
 *
 * @author: Suave
 * @date: 2019-12-24 13:43
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * "/admin/login"(POST)用户登录方法
     *
     * @param input
     * @return
     */
    @PostMapping("login")
    public RespObj<UserLoginOutput> login(@RequestBody UserLoginInput input) {
        return RespObj.success(adminService.login(input));
    }

    /**
     * "/admin"(DELETE)用户退出登录
     *
     * @param request
     * @return
     */
    @Auth("ADMIN")
    @DeleteMapping("")
    public RespObj logout(HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        adminService.logout(email);
        return RespObj.success();
    }

    /**
     * "/admin"(GET)获取当前用户的信息
     *
     * @return
     */
    @Auth("ADMIN")
    @GetMapping("")
    public RespObj info(HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        return RespObj.success(adminService.info(email));
    }

    /**
     * "/admin/count"(GET)获取用户总数、文章总数和标签总数
     *
     * @return
     */
    @Auth("ADMIN")
    @GetMapping("count")
    public RespObj<AdminCountOutput> count() {
        return RespObj.success(adminService.countAll());
    }
}
