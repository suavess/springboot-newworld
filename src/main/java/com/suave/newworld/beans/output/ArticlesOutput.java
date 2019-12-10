package com.suave.newworld.beans.output;

import cn.hutool.core.date.DateTime;
import com.suave.newworld.beans.Tags;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: Suave
 * @date: 2019-12-09 9:36
 */
@Data
@Accessors(chain = true)
public class ArticlesOutput {
    private String id;
    private String title;
    private String description;
    private String body;
    private DateTime createdAt;
    private DateTime updatedAt;
    private int favoritesCount;
    private boolean favorited;
    private Author author;
    private List<Tags> tagList;
}
