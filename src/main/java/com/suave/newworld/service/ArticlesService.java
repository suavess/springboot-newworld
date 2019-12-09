package com.suave.newworld.service;

import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.Articles;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suave.newworld.beans.input.ArticlesFeedListInput;
import com.suave.newworld.beans.input.ArticlesListInput;
import com.suave.newworld.beans.output.ArticlesOutput;
import com.suave.newworld.exception.RespException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
public interface ArticlesService extends IService<Articles> {

    /**
     * 分页获取文章列表
     * @param input
     * @return
     * @throws RespException
     */
    Page<ArticlesOutput> articlesList(ArticlesListInput input) throws RespException;

    /**
     * 分页获取所关注用户的文章
     * @param input
     * @return
     * @throws RespException
     */
    Page<ArticlesOutput> articlesFeedList(ArticlesFeedListInput input) throws RespException;

    /**
     * 通过id查询文章
     * @param id
     * @return
     * @throws RespException
     */
    ArticlesOutput findArticleById(String id) throws RespException;
}
