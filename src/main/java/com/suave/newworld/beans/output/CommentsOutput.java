package com.suave.newworld.beans.output;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-10 14:15
 */
@Data
@Accessors(chain = true)
public class CommentsOutput {
    private int id;
    private DateTime createdAt;
    private DateTime updatedAt;
    private String body;
    private Author author;
}
