package com.module.admin.service;

import com.base.dao.User;
import com.base.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author :jty
 * @date :20-8-1
 * @description : feign 服务调用接口 @FeignClient(value = "user-server")  value 为目标服务名
 * 方法需要与UserController定义请求方法一致，即UserController请求接口方法中有的SpringMVC注解，本接口一定要有。
 */
@Component
@FeignClient(value = "user-server")
public interface UserFeignService {
    /**
     * 根据用户id查询用户
     * @param userId
     * @return Result
     */
    @GetMapping(value = "/get/user/{userId}", produces = "application/json;charset=utf-8")
    Result searchUser(@PathVariable(value = "userId") int userId);

    /**
     * 新增用户
     * @param user
     * @return Result
     */
    @PostMapping(value = "/post/create/user", produces = "application/json;charset=utf-8")
    Result createUser(@RequestBody User user);

    /**
     * OpenFeign 超市请求测试
     * @return Result
     */
    @GetMapping(value = "/get/user/timeout")
    Result userServiceTimeOut();
}
