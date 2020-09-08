package com.module.admin.service;

import com.base.dao.User;
import com.base.entity.Result;
import com.module.admin.service.impl.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author :jty
 * @date :20-9-9
 * @description :feign远程调用接口
 */
@Component
@FeignClient(value = "user-server", fallback = UserServiceImpl.class)
public interface UserService {
    /**
     * 查询用户
     * @param userId
     * @return Result
     */
    @GetMapping(value = "/get/user/{userId}", produces = "application/json;charset=utf-8")
    Result searchUser(@PathVariable(value = "userId") int userId);
    /**
     * 添加用户
     * @param user
     * @return Result
     */
    @PostMapping(value = "/post/create/user", produces = "application/json;charset=utf-8")
    Result createUser(@RequestBody User user) ;
}
