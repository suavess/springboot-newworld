package com.suave.newworld.beans.input;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-13 16:16
 */
@Data
@Accessors(chain = true)
public class CommentsDeleteInput {
    private Integer aid;
    private Integer cid;
}
