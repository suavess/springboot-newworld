package com.suave.newworld.service;

import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.Tags;

/**
 * @author Suave
 * @date 2019-12-27 14:25
 */
public interface AdminTagsService {
    /**
     * 管理端获取标签列表
     *
     * @param input
     * @return
     */
    Page<Tags> list(Page input);

    /**
     * 通过id删除标签，同时删除关联表中的标签id
     * @param id
     */
    void del(Integer id);
}
