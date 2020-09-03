package com.module.user.mapper;

import com.base.dao.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author :jty
 * @date :20-7-20
 * @description :user mapper接口
 */
@Mapper
public interface UserMapper extends Mapper {
    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return User
     */
    User getUserById(int userId);

    /**
     * 创建用户
     * @param user
     * @return
     */
    int createUser(User user);
}
