package com.suave.newworld.service;

import com.suave.newworld.beans.Comments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suave.newworld.beans.input.CommentsCreateInput;
import com.suave.newworld.beans.output.CommentsOutput;
import com.suave.newworld.exception.RespException;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
public interface CommentsService extends IService<Comments> {

    /**
     * 添加评论
     * @param input
     * @param email
     * @throws RespException
     */
    void create(CommentsCreateInput input,String email) throws RespException;

    /**
     * 获取某一篇文章的所有评论
     * @param id 文章id
     * @return
     * @throws RespException
     */
    List<CommentsOutput> list(Integer id) throws RespException;

    /**
     * 删除文章中的某一条评论
     * @param email  用户邮箱
     * @throws RespException
     */
    void del(Integer aid, Integer cid,String email) throws RespException;
}
