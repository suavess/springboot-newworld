package com.suave.newworld.controller.admin;

import com.suave.newworld.annotation.Auth;
import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.input.AdminArticlesListInput;
import com.suave.newworld.beans.output.ArticlesOutput;
import com.suave.newworld.service.AdminArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理端对用户文章的操作
 *
 * @author: Suave
 * @date: 2019-12-25 10:17
 */
@RestController
@RequestMapping("/admin/articles")
public class AdminArticleController {

    @Autowired
    private AdminArticleService adminArticleService;

    /**
     * "/admin/articles"(GET)返回文章列表，分页
     *
     * @param input
     * @return
     */
    @Auth("ADMIN")
    @GetMapping("")
    public RespObj<Page<ArticlesOutput>> list(AdminArticlesListInput input) {
        return RespObj.success(adminArticleService.list(input));
    }

    /**
     * "/admin/articles"(DELETE)删除文章
     *
     * @param input
     * @return
     */
    @Auth("ADMIN")
    @DeleteMapping("")
    public RespObj del(@RequestBody Map<String, Object> input) {
        adminArticleService.delById((Integer) input.get("id"));
        return RespObj.success();
    }
}