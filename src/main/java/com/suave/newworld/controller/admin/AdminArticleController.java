package com.suave.newworld.controller.admin;

import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.input.AdminArticlesListInput;
import com.suave.newworld.beans.input.ArticlesListInput;
import com.suave.newworld.beans.output.ArticlesOutput;
import com.suave.newworld.service.AdminArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
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
     * @param input
     * @return
     */
    @GetMapping("")
    public RespObj<Page<ArticlesOutput>> list(AdminArticlesListInput input) {
        return RespObj.success(adminArticleService.list(input));
    }
}