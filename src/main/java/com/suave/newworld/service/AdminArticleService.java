package com.suave.newworld.service;

import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.input.AdminArticlesListInput;
import com.suave.newworld.beans.output.ArticlesOutput;

/**
 * @author: Suave
 * @date: 2019-12-25 10:58
 */
public interface AdminArticleService {

    /**
     * 管理端文章管理，分页返回
     *
     * @param input
     * @return
     */
    Page<ArticlesOutput> list(AdminArticlesListInput input);

    /**
     * 通过id删除文章
     * @param id
     */
    void delById(Integer id);
}
