package com.suave.newworld.beans.input;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-07 16:03
 */
@Data
@Accessors(chain = true)
public class UserUpdateInput {
    private String username;
    private String email;
    private String password;
    private String image;
    private String bio;
}
