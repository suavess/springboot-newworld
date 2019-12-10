package com.suave.newworld.beans.input;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-10 13:56
 */
@Data
@Accessors(chain = true)
public class CommentsCreateInput {
    /**
     * 文章id
     */
    private Integer id;

    /**
     * 评论内容
     */
    private String body;
}
