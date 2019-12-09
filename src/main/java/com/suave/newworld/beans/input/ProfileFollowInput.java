package com.suave.newworld.beans.input;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-07 18:25
 */
@Data
@Accessors(chain = true)
public class ProfileFollowInput {
    private String email;
}
