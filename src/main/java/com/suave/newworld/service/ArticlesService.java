package com.suave.newworld.service;

import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.Articles;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suave.newworld.beans.input.ArticleUpdateInput;
import com.suave.newworld.beans.input.ArticlesCreateInput;
import com.suave.newworld.beans.input.ArticlesFeedListInput;
import com.suave.newworld.beans.input.ArticlesListInput;
import com.suave.newworld.beans.output.ArticlesOutput;
import com.suave.newworld.exception.RespException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
public interface ArticlesService extends IService<Articles> {

    /**
     * 分页获取文章列表
     *
     * @param input
     * @return
     * @throws RespException
     */
    Page<ArticlesOutput> articlesList(ArticlesListInput input) throws RespException;

    /**
     * 登录过后请求的接口，需要返回是否收藏该文章
     * @param input
     * @param email
     * @return
     * @throws RespException
     */
    Page<ArticlesOutput> articlesListWithLogin(ArticlesListInput input,String email) throws RespException;

    /**
     * 分页获取所关注用户的文章
     *
     * @param input
     * @return
     * @throws RespException
     */
    Page<ArticlesOutput> articlesFeedList(ArticlesFeedListInput input) throws RespException;

    /**
     * 通过id查询文章
     *
     * @param id
     * @return
     * @throws RespException
     */
    ArticlesOutput findArticleById(Integer id) throws RespException;

    /**
     * 新建文章
     *
     * @param input
     * @param email
     * @throws RespException
     */
    void createArticle(ArticlesCreateInput input, String email) throws RespException;

    /**
     * 更新文章
     *
     * @param id
     * @param input
     * @param email
     * @return ArticlesOutput
     * @throws RespException
     */
    ArticlesOutput updateArticleById(Integer id, ArticleUpdateInput input, String email) throws RespException;

    /**
     * 通过Id删除文章
     *
     * @param id
     * @param email
     * @throws RespException
     */
    void deleteArticleById(Integer id, String email) throws RespException;

    /**
     * 收藏某一篇文章
     *
     * @param id
     * @param email
     * @return ArticlesOutput
     * @throws RespException
     */
    ArticlesOutput favorite(Integer id, String email) throws RespException;

    /**
     * 取消收藏某一篇文章
     *
     * @param id
     * @param email
     * @return ArticlesOutput
     * @throws RespException
     */
    ArticlesOutput unFavorite(Integer id, String email) throws RespException;
}
