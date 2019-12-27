package com.suave.newworld.beans.input;

import com.suave.newworld.beans.Page;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-27 10:30
 */
@Data
@Accessors(chain = true)
public class AdminUserInput extends Page {
    private String email;
    private String username;
    private String bio;
}
