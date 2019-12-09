package com.suave.newworld.service.impl;

import com.suave.newworld.beans.input.ProfileFollowInput;
import com.suave.newworld.beans.input.ProfileGetInput;
import com.suave.newworld.beans.output.ProfileGetOutput;
import com.suave.newworld.dao.ProfileMapper;
import com.suave.newworld.dao.UserMapper;
import com.suave.newworld.exception.RespException;
import com.suave.newworld.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Suave
 * @date: 2019-12-07 17:31
 */
@Service("ProfileService")
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileMapper profileMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 获取目标用户信息
     * @param input
     * @return
     * @throws RespException
     */
    @Override
    public ProfileGetOutput get(ProfileGetInput input,String email) throws RespException {
        String youEmail = input.getEmail();
        ProfileGetOutput profileGetOutput = profileMapper.get(youEmail);
        if (email != null){
            Integer follow = profileMapper.getFollow(email, youEmail);
            if (follow==1){
                profileGetOutput.setFollowing(true);
            }
        }
        return profileGetOutput;
    }

    /**
     * 关注用户
     * @param input
     * @param email
     * @throws RespException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void follow(ProfileFollowInput input, String email) throws RespException {
        Integer myId = userMapper.findIdByEmail(email);
        Integer yourId = userMapper.findIdByEmail(input.getEmail());
        profileMapper.follow(myId,yourId);
    }

    /**
     * 取消关注某个用户
     * @param input
     * @param email
     * @throws RespException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void unFollow(ProfileFollowInput input, String email) throws RespException {
        Integer myId = userMapper.findIdByEmail(email);
        Integer yourId = userMapper.findIdByEmail(input.getEmail());
        profileMapper.unFollow(myId,yourId);
    }
}
