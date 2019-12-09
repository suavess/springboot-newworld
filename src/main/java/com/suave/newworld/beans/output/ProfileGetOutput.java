package com.suave.newworld.beans.output;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Suave
 * @date: 2019-12-07 17:26
 */
@Data
@Accessors(chain = true)
public class ProfileGetOutput {
    private String username;
    private String bio;
    private String image;
    private boolean following;
}
