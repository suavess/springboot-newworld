package com.suave.newworld.service.impl;

import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.input.AdminArticlesListInput;
import com.suave.newworld.beans.output.ArticlesOutput;
import com.suave.newworld.dao.ArticlesMapper;
import com.suave.newworld.service.AdminArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Suave
 * @date: 2019-12-25 10:59
 */
@Service
public class AdminArticleServiceImpl implements AdminArticleService {

    @Autowired
    private ArticlesMapper articlesMapper;

    @Override
    public Page<ArticlesOutput> list(AdminArticlesListInput input) {
        String author = input.getAuthor();
        String tags = input.getTags();
        // 查询多少条数据
        Integer limit = input.getSize();
        // 从哪里开始查询
        Integer offset = (input.getPage() - 1) * limit;
        Page<ArticlesOutput> output = new Page<>();
        Integer count = articlesMapper.adminArticleListCount(author, tags);
        output.setTotal(count);
        List<ArticlesOutput> articlesOutputs = articlesMapper.adminArticleList(author, tags, limit, offset);
        output.setRows(articlesOutputs);
        return output;
    }
}
