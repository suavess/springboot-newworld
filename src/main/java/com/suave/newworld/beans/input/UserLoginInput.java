package com.suave.newworld.beans.input;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Suave
 * @Date: 2019-11-29 14:11
 * @Desc:
 */
@Data
@Accessors(chain = true)
public class UserLoginInput {
    private String email;
    private String password;
}
