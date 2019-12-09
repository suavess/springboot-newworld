package com.suave.newworld.controller;

import com.suave.newworld.annotation.Auth;
import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.input.ArticlesFeedListInput;
import com.suave.newworld.beans.input.ArticlesListInput;
import com.suave.newworld.beans.output.ArticlesOutput;
import com.suave.newworld.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Suave
 * @since 2019-11-26
 */
@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    ArticlesService articlesService;

    /**
     * 返回文章列表，分页
     * @param input
     * @return
     */
    @GetMapping("")
    public RespObj<Page<ArticlesOutput>> list(@RequestBody(required = false) ArticlesListInput input) {
        if(input==null){
            input = new ArticlesListInput();
        }
        return RespObj.success(articlesService.articlesList(input));
    }

    /**
     * 返回所关注的用户的文章
     * @param input
     * @return
     */
    @Auth
    @GetMapping("feed")
    public RespObj<Page<ArticlesOutput>> feedList(@RequestBody(required = false) ArticlesFeedListInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        input.setEmail(email);
        return RespObj.success(articlesService.articlesFeedList(input));
    }

    /**
     * 传ID查询某一篇文章
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public RespObj<ArticlesOutput> article(@PathVariable String id){
        return RespObj.success(articlesService.findArticleById(id));
    }

}
