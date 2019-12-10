package com.suave.newworld.service.impl;

import cn.hutool.core.date.DateTime;
import com.suave.newworld.beans.Comments;
import com.suave.newworld.beans.input.CommentsCreateInput;
import com.suave.newworld.beans.output.CommentsOutput;
import com.suave.newworld.dao.CommentsMapper;
import com.suave.newworld.dao.UserMapper;
import com.suave.newworld.exception.RespError;
import com.suave.newworld.exception.RespException;
import com.suave.newworld.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentsMapper commentsMapper;

    /**
     * 添加评论
     *
     * @param input
     * @param email
     * @throws RespException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = "ArticleComments", key = "#input.getId()")
    public void create(CommentsCreateInput input, String email) throws RespException {
        Integer userId = userMapper.findIdByEmail(email);
        Comments comments = new Comments();
        comments.setArticleId(input.getId())
                .setBody(input.getBody())
                .setUserId(userId)
                .setCreatedAt(DateTime.now())
                .setUpdatedAt(DateTime.now());
        commentsMapper.insert(comments);
    }

    @Override
    @Cacheable(value = "ArticleComments", key = "#id")
    public List<CommentsOutput> list(Integer id) throws RespException {
        List<CommentsOutput> outputs = commentsMapper.findCommentsByArticleId(id);
        return outputs;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = "ArticleComments", key = "#aid")
    public void del(Integer aid, Integer cid, String email) throws RespException {
        Integer userId = userMapper.findIdByEmail(email);
        Comments comments = commentsMapper.selectById(cid);
        if (!comments.getUserId().equals(userId)) {
            throw new RespException(RespError.CUSTOM_ERROR, "您没有资格删除该评论！");
        }
        if (!comments.getArticleId().equals(aid)) {
            throw new RespException(RespError.CUSTOM_ERROR, "文章评论格式错误！");
        }
        commentsMapper.deleteById(cid);
    }
}
