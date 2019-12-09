package com.suave.newworld.beans.input;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-02 17:30
 */
@Data
@Accessors(chain = true)
public class UserRegisterInput {

    private String username;

    private String password;

    private String email;

}
