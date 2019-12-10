package com.suave.newworld.dao;

import com.suave.newworld.beans.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suave.newworld.beans.output.CommentsOutput;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
public interface CommentsMapper extends BaseMapper<Comments> {
    /**
     * 查询某一篇文章的评论
     * @param id
     * @return
     */
    List<CommentsOutput> findCommentsByArticleId(@Param("id") Integer id);

}
