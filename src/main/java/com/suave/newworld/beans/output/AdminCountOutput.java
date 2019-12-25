package com.suave.newworld.beans.output;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-24 17:22
 */
@Data
@Accessors(chain = true)
public class AdminCountOutput {
    private Integer userCount;
    private Integer articleCount;
    private Integer tagsCount;
}
