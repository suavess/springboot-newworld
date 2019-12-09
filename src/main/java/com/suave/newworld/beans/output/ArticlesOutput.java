package com.suave.newworld.beans.output;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Timestamp createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Timestamp updatedAt;
    private int favoritesCount;
    private boolean favorited;
    private Author author;
    private List<String> tagList;
}
