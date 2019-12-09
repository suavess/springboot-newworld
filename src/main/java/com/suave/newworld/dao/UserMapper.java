package com.suave.newworld.dao;

import com.suave.newworld.beans.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过email查询id
     * @param email
     * @return
     */
    @Select("select id from users where email = #{email}")
    Integer findIdByEmail(String email);
}
