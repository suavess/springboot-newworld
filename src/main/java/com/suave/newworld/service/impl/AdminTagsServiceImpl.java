package com.suave.newworld.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.Tags;
import com.suave.newworld.dao.ArticlesMapper;
import com.suave.newworld.dao.TagsMapper;
import com.suave.newworld.service.AdminTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Suave
 * @date 2019-12-27 14:27
 */
@Service
public class AdminTagsServiceImpl implements AdminTagsService {

    @Autowired
    private TagsMapper tagsMapper;

    @Override
    public Page<Tags> list(Page input) {
        // 查询多少条数据
        Integer limit = input.getSize();
        // 从哪里开始查询
        Integer offset = (input.getPage() - 1) * limit;
        Integer count = tagsMapper.selectCount(null);
        QueryWrapper<Tags> wrapper = new QueryWrapper<>();
        wrapper.last(String.format("limit %s,%s", offset, limit));
        List<Tags> tags = tagsMapper.selectList(wrapper);
        Page<Tags> output = new Page<>();
        output.setTotal(count)
                .setPage(input.getPage())
                .setSize(input.getSize())
                .setRows(tags);
        return output;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void del(Integer id) {
        // 删除tags表中的记录
        tagsMapper.deleteById(id);
        // 删除tags和articles的关联表的记录
        tagsMapper.delArticleTagsByTagsId(id);
    }
}
