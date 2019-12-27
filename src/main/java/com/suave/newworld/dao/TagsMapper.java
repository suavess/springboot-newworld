package com.suave.newworld.dao;

import com.suave.newworld.beans.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
public interface TagsMapper extends BaseMapper<Tags> {
    /**
     * 通过tagId删除关联表中的关联信息
     * @param id
     */
    void delArticleTagsByTagsId(@Param("id") Integer id);
}
