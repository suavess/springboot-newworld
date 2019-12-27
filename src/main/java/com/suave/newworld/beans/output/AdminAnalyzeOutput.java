package com.suave.newworld.beans.output;

import com.suave.newworld.beans.Articles;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author: Suave
 * @date: 2019-12-27 17:23
 */
@Data
@Accessors(chain = true)
public class AdminAnalyzeOutput {
    private List<String> columns;
    private List<Map<String,Object>> rows;
}
