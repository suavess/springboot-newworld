package com.suave.newworld.controller;

import com.suave.newworld.annotation.Auth;
import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.input.ProfileFollowInput;
import com.suave.newworld.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户之间的各种操作
 *
 * @author Suave
 * @date 2019-12-07 17:17
 */
@RestController
@RequestMapping("/profiles")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    /**
     * "/profiles"(GET)通过id获取某个用户的信息，以及关注关系
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("")
    public RespObj get(@RequestParam("id") Integer id, HttpServletRequest request) {
        Object o = request.getAttribute("email");
        String email = null;
        if (o != null) {
            email = o.toString();
        }
        return RespObj.success(profileService.get(id, email));
    }

    /**
     * "/profiles/follow"(POST)关注某个用户
     *
     * @param input
     * @param request
     * @return
     */
    @Auth
    @PostMapping("follow")
    public RespObj follow(@RequestBody ProfileFollowInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        profileService.follow(input, email);
        return RespObj.success();
    }

    /**
     * "/profiles/follow"(DELETE)取关某个用户
     *
     * @param input
     * @param request
     * @return
     */
    @Auth
    @DeleteMapping("follow")
    public RespObj unFollow(@RequestBody ProfileFollowInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        profileService.unFollow(input, email);
        return RespObj.success();
    }

}
