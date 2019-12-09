package com.suave.newworld.service;

import com.suave.newworld.beans.input.ProfileFollowInput;
import com.suave.newworld.beans.input.ProfileGetInput;
import com.suave.newworld.beans.output.ProfileGetOutput;
import com.suave.newworld.exception.RespException;

/**
 * 用户之间相关操作
 * @author: Suave
 * @date: 2019-12-07 17:25
 */
public interface ProfileService {
    /**
     * 获取目标用户相关信息
     * @param input
     * @param email
     * @return
     * @throws RespException
     */
    ProfileGetOutput get(ProfileGetInput input,String email) throws RespException;

    /**
     * 关注用户
     * @param input
     * @param email
     * @throws RespException
     */
    void follow(ProfileFollowInput input,String email) throws RespException;

    /**
     * 取消关注用户
     * @param email
     * @param input
     * @throws RespException
     */
    void unFollow(ProfileFollowInput input,String email) throws RespException;
}
