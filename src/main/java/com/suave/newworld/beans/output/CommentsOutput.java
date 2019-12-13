package com.suave.newworld.beans.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: Suave
 * @date: 2019-12-10 14:15
 */
@Data
@Accessors(chain = true)
public class CommentsOutput {
    private int id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updatedAt;
    private String body;
    private Author author;
}
