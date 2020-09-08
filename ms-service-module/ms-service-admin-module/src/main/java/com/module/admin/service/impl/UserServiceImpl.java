package com.module.admin.service.impl;

import com.base.dao.User;
import com.base.entity.Result;
import com.module.admin.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author :jty
 * @date :20-9-9
 * @description : 服务降级熔断实现
 */
@Component
public class UserServiceImpl implements UserService {
    /**
     * 查询用户
     * @param userId
     * @return Result
     */
    @Override
    @GetMapping(value = "/get/user/{userId}", produces = "application/json;charset=utf-8")
    public Result searchUser(@PathVariable(value = "userId") int userId) {
        return new Result(-200, "服务降级或熔断",null);
    }
    /**
     * 添加用户
     * @param user
     * @return Result
     */
    @Override
    @PostMapping(value = "/post/create/user", produces = "application/json;charset=utf-8")
    public Result createUser(@RequestBody User user) {
        return new Result(-200, "服务降级或熔断",null);
    }
}
