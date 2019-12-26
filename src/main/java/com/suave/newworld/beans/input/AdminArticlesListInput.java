package com.suave.newworld.beans.input;

import com.suave.newworld.beans.Page;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-25 10:22
 */
@Data
@Accessors(chain = true)
public class AdminArticlesListInput extends Page {
    /**
     * 文章的标签
     */
    private String tags;
    /**
     * 文章作者的username
     */
    private String author;
}
