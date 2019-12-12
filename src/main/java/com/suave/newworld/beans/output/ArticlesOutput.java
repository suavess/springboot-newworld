package com.suave.newworld.beans.output;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.suave.newworld.beans.Tags;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author: Suave
 * @date: 2019-12-09 9:36
 */
@Data
@Accessors(chain = true)
public class ArticlesOutput {
    private Integer id;
    private String title;
    private String description;
    private String body;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updatedAt;
    private int favoritesCount;
    private boolean favorited;
    private Author author;
    private List<Tags> tagList;
}
