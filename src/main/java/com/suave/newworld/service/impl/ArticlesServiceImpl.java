package com.suave.newworld.service.impl;

import cn.hutool.core.util.StrUtil;
import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.Articles;
import com.suave.newworld.beans.input.ArticlesFeedListInput;
import com.suave.newworld.beans.input.ArticlesListInput;
import com.suave.newworld.beans.output.ArticlesOutput;
import com.suave.newworld.dao.ArticlesMapper;
import com.suave.newworld.dao.UserMapper;
import com.suave.newworld.exception.RespException;
import com.suave.newworld.service.ArticlesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesMapper, Articles> implements ArticlesService {

    @Autowired
    ArticlesMapper articlesMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 分页获取文章列表
     *
     * @param input
     * @return
     * @throws RespException
     */
    @Override
    @Cacheable(value = "Page<ArticlesOutput>")
    public Page<ArticlesOutput> articlesList(ArticlesListInput input) throws RespException {
        // 标签Id
        Integer tag = null;
        if (!StrUtil.isEmpty(input.getTag())) {
            tag = articlesMapper.findTagIdByName(input.getTag());
        }
        // 作者Id
        Integer author = null;
        if (!StrUtil.isEmpty(input.getAuthor())) {
            author = userMapper.findIdByEmail(input.getAuthor());
        }
        // 某个用户id，收藏的文章
        Integer favorited = null;
        if (!StrUtil.isEmpty(input.getFavorited())) {
            favorited = userMapper.findIdByEmail(input.getFavorited());
        }
        // 查询多少条数据
        Integer limit = input.getSize();
        // 从哪里开始查询
        Integer offset = (input.getPage() - 1) * limit;
        // 满足条件的文章总数量
        Integer total = articlesMapper.articlesCount(tag, author, favorited);
        // 文章列表
        List<ArticlesOutput> list = articlesMapper.findArticlesList(offset,limit,tag,author,favorited);
        Page<ArticlesOutput> articlesListOutput = new Page<>();
        articlesListOutput.setPage(input.getPage());
        articlesListOutput.setSize(input.getSize());
        articlesListOutput.setTotal(total);
        articlesListOutput.setRows(list);
        return articlesListOutput;
    }

    /**
     * 查询关注用户的文章列表
     * @param input
     * @return
     * @throws RespException
     */
    @Override
    @Cacheable(value = "Page<ArticlesFeedOutput>")
    public Page<ArticlesOutput> articlesFeedList(ArticlesFeedListInput input) throws RespException {
        Integer id = userMapper.findIdByEmail(input.getEmail());
        // 查询多少条数据
        Integer limit = input.getSize();
        // 从哪里开始查询
        Integer offset = (input.getPage() - 1) * limit;
        // 满足条件的文章总数量
        Integer total = articlesMapper.articlesCountByFeed(id);
        List<ArticlesOutput> articlesFeedList = articlesMapper.findArticlesFeedList(offset, limit, id);

        Page<ArticlesOutput> page = new Page<>();
        page.setPage(input.getPage()).setSize(input.getSize()).setTotal(total).setRows(articlesFeedList);
        return page;
    }

    /**
     * 通过id查询文章
     * @param id
     * @return
     * @throws RespException
     */
    @Override
    public ArticlesOutput findArticleById(String id) throws RespException {
        return articlesMapper.findArticleById(id);
    }
}
