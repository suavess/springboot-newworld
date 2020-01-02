package com.suave.newworld.controller;


import com.suave.newworld.annotation.Auth;
import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.input.CommentsCreateInput;
import com.suave.newworld.beans.input.CommentsDeleteInput;
import com.suave.newworld.beans.output.CommentsOutput;
import com.suave.newworld.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    /**
     * "/comments"(POST)添加评论
     *
     * @param input
     * @param request
     * @return
     */
    @Auth
    @PostMapping("")
    public RespObj create(@RequestBody CommentsCreateInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        commentsService.create(input, email);
        return RespObj.success();
    }

    /**
     * "/comments/{id}"(GET)返回某一篇文章的所有评论
     *
     * @param id 文章id
     * @return
     */
    @GetMapping("{id}")
    public RespObj<List<CommentsOutput>> list(@PathVariable Integer id) {
        return RespObj.success(commentsService.list(id));
    }

    /**
     * "/comments"(Delete)返回某一篇文章的某一条评论
     *
     * @param input
     * @param request
     * @return
     */
    @Auth
    @DeleteMapping("")
    public RespObj del(@RequestBody CommentsDeleteInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        commentsService.del(input.getAid(), input.getCid(), email);
        return RespObj.success();
    }
}
