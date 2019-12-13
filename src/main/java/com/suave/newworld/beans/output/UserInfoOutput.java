package com.suave.newworld.beans.output;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-07 14:29
 */
@Data
@Accessors(chain = true)
public class UserInfoOutput {
    private Integer id;
    private String username;
    private String email;
    private String role;
    private String bio;
    private String image;

}
