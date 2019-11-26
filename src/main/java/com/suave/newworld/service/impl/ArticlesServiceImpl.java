package com.suave.newworld.service.impl;

import com.suave.newworld.beans.Articles;
import com.suave.newworld.dao.ArticlesMapper;
import com.suave.newworld.service.ArticlesService;
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
public class ArticlesServiceImpl extends ServiceImpl<ArticlesMapper, Articles> implements ArticlesService {

}
