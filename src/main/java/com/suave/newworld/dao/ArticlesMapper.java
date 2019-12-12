package com.suave.newworld.dao;

import com.suave.newworld.beans.Articles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suave.newworld.beans.Tags;
import com.suave.newworld.beans.output.ArticlesOutput;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
public interface ArticlesMapper extends BaseMapper<Articles> {

    /**
     * 通过tag名称找到tag的id
     *
     * @param tag
     * @return
     */
    @Select("select id from tags where tags.name = #{tag}")
    Integer findTagIdByName(@Param("tag") String tag);

    /**
     * 通过文章id查找taglist
     *
     * @param id
     * @return
     */
    List<Tags> findTagListByArticleId(@Param("id") Integer id);

    /**
     * 查询文章数量
     *
     * @param tag
     * @param author
     * @param favorited
     * @return
     */
    Integer articlesCount(@Param("tag") Integer tag, @Param("author") Integer author, @Param("favorited") Integer favorited);

    /**
     * 获取所关注用户的文章总数
     *
     * @param id
     * @return
     */
    Integer articlesCountByFeed(@Param("id") Integer id);

    /**
     * 获取文章列表
     *
     * @param offset    从第几条开始查
     * @param limit     一共查几条
     * @param tag       标签
     * @param author    作者
     * @param favorited 收藏者
     * @return
     */
    List<ArticlesOutput> findArticlesList(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("tag") Integer tag, @Param("author") Integer author, @Param("favorited") Integer favorited);


    /**
     * 查询关注用户的文章列表
     *
     * @param offset 从第几条开始查
     * @param limit  一共查几条
     * @param id     登录用户的id
     * @return
     */
    List<ArticlesOutput> findArticlesFeedList(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("id") Integer id);

    /**
     * 通过id查询文章
     *
     * @param id
     * @return
     */
    ArticlesOutput findArticleById(@Param("id") Integer id);

    /**
     * 通过文章id删除文章所有的标签
     *
     * @param id
     */
    void delTagsByArticleId(@Param("id") Integer id);

    /**
     * 通过文章id和标签列表插入标签
     *
     * @param id      文章id
     * @param tagList 标签列表
     */
    void insertTagsByArticleId(@Param("id") Integer id, @Param("tagList") List<Integer> tagList);

    /**
     * 收藏某篇文章
     *
     * @param uid 用户id
     * @param aid 文章id
     */
    void insertFavoriteArticle(@Param("uid") Integer uid, @Param("aid") Integer aid);

    /**
     * 取消收藏某篇文章
     *
     * @param uid 用户id
     * @param aid 文章id
     */
    void delFavoriteArticle(@Param("uid") Integer uid, @Param("aid") Integer aid);
}
