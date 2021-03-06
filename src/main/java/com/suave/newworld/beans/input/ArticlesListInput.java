package com.suave.newworld.beans.input;

import com.suave.newworld.beans.Page;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-09 9:22
 */
@Data
@Accessors(chain = true)
public class ArticlesListInput extends Page {
    private String tag;
    /**
     * 文章作者的email
     */
    private String author;
    /**
     * 被某个用户收藏
     */
    private String favorited;
}
