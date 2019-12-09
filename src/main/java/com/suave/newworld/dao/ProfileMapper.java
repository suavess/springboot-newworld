package com.suave.newworld.dao;

import com.suave.newworld.beans.input.ProfileGetInput;
import com.suave.newworld.beans.output.ProfileGetOutput;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 *
 * @author: Suave
 * @date: 2019-12-07 17:42
 */
public interface ProfileMapper {
    /**
     * 通过email获取用户信息
     * @param email
     * @return
     */
    @Select("select * from users where email = #{email}")
    ProfileGetOutput get(String email);

    /**
     * 获取用户关注关系
     * @param myEmail
     * @param yourEmail
     * @return
     */
    @Select("SELECT count( 1 ) FROM `follows` as f " +
            "WHERE f.user_id = (SELECT id FROM users where email=#{myEmail}) " +
            "and f.follow_id = (SELECT id FROM users where email=#{yourEmail})")
    Integer getFollow(String myEmail,String yourEmail);

    /**
     * 关注某个用户
     * @param myId
     * @param yourId
     */
    @Insert("insert into follows values (#{myId},#{yourId})")
    void follow(Integer myId,Integer yourId);

    /**
     * 取消关注某个用户
     * @param myId
     * @param yourId
     */
    @Delete("DELETE from follows where user_id=#{myid} and follow_id=#{yourId}")
    void unFollow(Integer myId,Integer yourId);
}
