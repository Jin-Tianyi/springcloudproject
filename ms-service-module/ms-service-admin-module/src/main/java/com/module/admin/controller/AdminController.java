package com.module.admin.controller;

import com.base.dao.User;
import com.base.entity.Result;
import com.module.admin.service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author :jty
 * @date :20-7-28
 * @description : 管理员模块
 */
@RestController
public class AdminController {
    @Autowired
    UserFeignService userFeignService;

    /**
     * RestTemplate forObject
     */
    @GetMapping(value = "/admin/get/user/{userId}", produces = "application/json;charset=utf-8")
    public Result searchUser(@PathVariable int userId) {
        Result result = userFeignService.searchUser(userId);
        return result;
    }

    @GetMapping(value = "/admin/post/create/user", produces = "application/json;charset=utf-8")
    public Result createUser(@RequestBody User user) {
        Result result = userFeignService.createUser(user);
        return result;
    }
    /**
     * OpenFeign 超市请求测试
     */
    @GetMapping(value = "/admin/get/user/timeout")
    public Result userServiceTimeOut(){
        Result result = userFeignService.userServiceTimeOut();
        return result;
    }
}
