package com.suave.newworld.service.impl;

import com.suave.newworld.beans.db.Comments;
import com.suave.newworld.dao.CommentsMapper;
import com.suave.newworld.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

}
