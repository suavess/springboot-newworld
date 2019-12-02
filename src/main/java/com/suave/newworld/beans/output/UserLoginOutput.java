package com.suave.newworld.beans.output;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Suave
 * @Date: 2019-11-29 14:08
 * @Desc:
 */

@Data
@Accessors(chain = true)
public class UserLoginOutput {
    private String token;
}
