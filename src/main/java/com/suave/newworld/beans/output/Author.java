package com.suave.newworld.beans.output;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文章列表中的作者类
 *
 * @author: Suave
 * @date: 2019-12-09 9:45
 */
@Data
@Accessors(chain = true)
public class Author {
    private Integer id;
    private String username;
    private String bio;
    private String image;
    private boolean following;
}
