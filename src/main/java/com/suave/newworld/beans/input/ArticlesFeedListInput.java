package com.suave.newworld.beans.input;

import com.suave.newworld.beans.Page;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-09 15:00
 */
@Data
@Accessors(chain = true)
public class ArticlesFeedListInput extends Page {
    private String email;
}
