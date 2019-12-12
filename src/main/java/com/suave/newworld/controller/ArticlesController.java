package com.suave.newworld.controller;

import cn.hutool.core.util.StrUtil;
import com.suave.newworld.annotation.Auth;
import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.input.*;
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
     * "/articles"(GET)返回文章列表，分页
     *
     * @param input
     * @return
     */
    @GetMapping("")
    public RespObj<Page<ArticlesOutput>> list(ArticlesListInput input, HttpServletRequest request) {
        if (input == null) {
            input = new ArticlesListInput();
        }
        Object email = request.getAttribute("email");
        if (email == null) {
            return RespObj.success(articlesService.articlesList(input));
        } else {
            return RespObj.success(articlesService.articlesListWithLogin(input, email.toString()));
        }
    }

    /**
     * "/articles"(POST)新建文章
     *
     * @param input
     * @param request
     * @return
     */
    @Auth
    @PostMapping("")
    public RespObj create(@RequestBody ArticlesCreateInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        articlesService.createArticle(input, email);
        return RespObj.success();
    }

    /**
     * "/articles/feed"(GET)返回所关注的用户的文章
     *
     * @param input
     * @return
     */
    @Auth
    @GetMapping("feed")
    public RespObj<Page<ArticlesOutput>> feedList(ArticlesFeedListInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        input.setEmail(email);
        return RespObj.success(articlesService.articlesFeedList(input));
    }

    /**
     * "/articles/{id}"(GET)传ID查询某一篇文章
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public RespObj<ArticlesOutput> article(@PathVariable Integer id) {
        return RespObj.success(articlesService.findArticleById(id));
    }

    /**
     * "/articles/{id}"(PUT)传ID更新某一篇文章
     *
     * @param id
     * @param input
     * @param request
     * @return
     */
    @Auth
    @PutMapping("{id}")
    public RespObj update(@PathVariable Integer id, @RequestBody ArticleUpdateInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        articlesService.updateArticleById(id, input, email);
        return RespObj.success();
    }

    /**
     * /articles/{id}"(DELETE)传ID删除某一篇文章
     *
     * @param id
     * @return
     */
    @Auth
    @DeleteMapping("{id}")
    public RespObj del(@PathVariable Integer id, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        articlesService.deleteArticleById(id, email);
        return RespObj.success();
    }

    /**
     * /articles/favorite"(POST)传Id收藏某一篇文章
     *
     * @param input
     * @param request
     * @return
     */
    @Auth
    @PostMapping("favorite")
    public RespObj favorite(@RequestBody ArticlesFavoriteInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        articlesService.favorite(input.getId(), email);
        return RespObj.success();
    }

    /**
     * /articles/favorite"(DELETE)传Id取消收藏某一篇文章
     *
     * @param input
     * @param request
     * @return
     */
    @Auth
    @DeleteMapping("favorite")
    public RespObj unFavorite(@RequestBody ArticlesFavoriteInput input, HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        articlesService.unFavorite(input.getId(), email);
        return RespObj.success();
    }
}
