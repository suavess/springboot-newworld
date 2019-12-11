package com.suave.newworld.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.newworld.beans.Articles;
import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.Tags;
import com.suave.newworld.beans.input.ArticleUpdateInput;
import com.suave.newworld.beans.input.ArticlesCreateInput;
import com.suave.newworld.beans.input.ArticlesFeedListInput;
import com.suave.newworld.beans.input.ArticlesListInput;
import com.suave.newworld.beans.output.ArticlesOutput;
import com.suave.newworld.dao.ArticlesMapper;
import com.suave.newworld.dao.TagsMapper;
import com.suave.newworld.dao.UserMapper;
import com.suave.newworld.exception.RespError;
import com.suave.newworld.exception.RespException;
import com.suave.newworld.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    TagsMapper tagsMapper;

    /**
     * 分页获取文章列表
     *
     * @param input
     * @return
     * @throws RespException
     */
    @Override
    @Cacheable(value = "ArticlesList", key = "#p0")
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
        List<ArticlesOutput> list = articlesMapper.findArticlesList(offset, limit, tag, author, favorited);
        Page<ArticlesOutput> articlesListOutput = new Page<>();
        articlesListOutput.setPage(input.getPage());
        articlesListOutput.setSize(input.getSize());
        articlesListOutput.setTotal(total);
        articlesListOutput.setRows(list);
        return articlesListOutput;
    }

    /**
     * 查询关注用户的文章列表
     *
     * @param input
     * @return
     * @throws RespException
     */
    @Override
    @Cacheable(value = "ArticlesFeedList")
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
     *
     * @param id
     * @return
     * @throws RespException
     */
    @Override
    @Cacheable(value = "ArticleById", key = "#p0")
    public ArticlesOutput findArticleById(Integer id) throws RespException {
        return articlesMapper.findArticleById(id);
    }

    /**
     * 通过id更新文章
     *
     * @param id 文章id
     * @param input
     * @param email 用户email
     * @return
     * @throws RespException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CachePut(value = "ArticleById", key = "#p0")
    public ArticlesOutput updateArticleById(Integer id, ArticleUpdateInput input, String email) throws RespException {
        Integer userId = userMapper.findIdByEmail(email);
        Articles articleById = articlesMapper.selectById(id);
        // 判断该文章的userId是否和当前用户的id相同
        if (articleById.getUserId().equals(userId)) {
            throw new RespException(RespError.CUSTOM_ERROR,"您没有权限修改该文章！");
        }
        // 更新文章的标签
        List<Integer> tagList = input.getTagList();
        List<Tags> tags = tagsMapper.selectList(null);
        List<Integer> tmp = new ArrayList<>();
        tags.forEach(tag->{
            tmp.add(tag.getId());
        });
        if (!tmp.containsAll(tagList)){
            throw new RespException(RespError.CUSTOM_ERROR,"标签列表无效！");
        }
        articlesMapper.delTagsByArticleId(id);
        articlesMapper.insertTagsByArticleId(id,tagList);
        // 更新文章
        Articles article = new Articles();
        BeanUtil.copyProperties(input,article);
        article.setId(id);
        articlesMapper.updateById(article);
        // 返回更新后的文章，同时会更新缓存中的该文章
        ArticlesOutput output = articlesMapper.findArticleById(id);
        return output;
    }

    /**
     * 创建文章
     *
     * @param input
     * @param email
     * @throws RespException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = {"ArticlesList", "ArticlesFeedList"}, allEntries = true)
    public void createArticle(ArticlesCreateInput input, String email) throws RespException {
        Articles articles = new Articles();
        if (StrUtil.isEmpty(input.getTitle())){
            throw new RespException(RespError.CUSTOM_ERROR,"请输入文章标题");
        }
        if (StrUtil.isEmpty(input.getDescription())){
            throw new RespException(RespError.CUSTOM_ERROR,"请输入文章描述");
        }
        if (StrUtil.isEmpty(input.getBody())){
            throw new RespException(RespError.CUSTOM_ERROR,"请输入文章内容");
        }
        if (input.getBody().length()==0){
            throw new RespException(RespError.CUSTOM_ERROR,"请至少选择一个文章标签");
        }
        BeanUtil.copyProperties(input, articles);
        DateTime now = DateTime.now();
        articles.setCreatedAt(now)
                .setUpdatedAt(now)
                .setUserId(userMapper.findIdByEmail(email));
        articlesMapper.insert(articles);
        List<Integer> tagList = input.getTagList();
        articlesMapper.insertTagsByArticleId(articles.getId(),tagList);
    }

    /**
     * 通过id删除文章
     * @param id
     * @param email
     * @throws RespException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = "ArticleById", key = "#p0")
    public void deleteArticleById(Integer id,String email) throws RespException {
        Integer userId = userMapper.findIdByEmail(email);
        Articles article = articlesMapper.selectById(id);
        if (!article.getUserId().equals(userId)){
            throw new RespException(RespError.CUSTOM_ERROR,"您没有权限删除该文章！");
        }
        articlesMapper.delTagsByArticleId(id);
        articlesMapper.deleteById(id);
    }

    @Override
    @CachePut(value = "ArticleById", key = "#aid")
    @CacheEvict(value = {"ArticlesList", "ArticlesFeedList"}, allEntries = true)
    public ArticlesOutput favorite(Integer aid, String email) throws RespException {
        Integer uid = userMapper.findIdByEmail(email);
        articlesMapper.insertFavoriteArticle(uid,aid);
        ArticlesOutput output = articlesMapper.findArticleById(aid);
        return output;
    }

    @Override
    @CachePut(value = "ArticleById", key = "#aid")
    @CacheEvict(value = {"ArticlesList", "ArticlesFeedList"}, allEntries = true)
    public ArticlesOutput unFavorite(Integer aid, String email) throws RespException {
        Integer uid = userMapper.findIdByEmail(email);
        articlesMapper.delFavoriteArticle(uid,aid);
        ArticlesOutput output = articlesMapper.findArticleById(aid);
        return output;
    }
}
