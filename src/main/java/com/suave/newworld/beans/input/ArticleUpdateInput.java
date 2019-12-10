package com.suave.newworld.beans.input;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: Suave
 * @date: 2019-12-09 17:06
 */
@Data
@Accessors(chain = true)
public class ArticleUpdateInput {
    private String title;
    private String description;
    private String body;
    private List<Integer> tagList;
}
