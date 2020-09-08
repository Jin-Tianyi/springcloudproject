package com.module.admin.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.base.dao.User;
import com.base.entity.Result;
import com.module.admin.sentinel.UserSentinel;
import com.module.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.alibaba.cloud.sentinel.datasource.RuleType;
import javax.annotation.Resource;

/**
 * @author :jty
 * @date :20-7-28
 * @description : 管理员模块
 */
@RestController
public class AdminController {
    @Autowired
    private RestTemplate restTemplate;
    /** 服务提供者地址 */
    @Value("${provider-service-url.user-service}")
    private String userServiceUrl;
    @Resource
    private UserService userService;

    /** feign 调用，feign 服务降级和熔断*/
    @GetMapping(value = "/admin/find/user/{userId}", produces = "application/json;charset=utf-8")
    public Result searchUser(@PathVariable int userId) {
        Result result = userService.searchUser(userId);
        return result;
    }
    /** restTemplate 调用，sentinel 服务降级和熔断 */
    @GetMapping(value = "/admin/get/user/{userId}", produces = "application/json;charset=utf-8")
    @SentinelResource(value = "/admin/get/user/", blockHandler = "blockHandler",
            blockHandlerClass = UserSentinel.class, fallback = "fallback")
    public Result getUser(@PathVariable int userId) {
        //int c = 10/0;
        Result result = restTemplate.getForObject(userServiceUrl + "/get/user/" + userId, Result.class);
        return result;
    }
    @GetMapping(value = "/admin/post/create/user", produces = "application/json;charset=utf-8")
    @SentinelResource(value = "/admin/post/create/user", blockHandler = "blockHandler",
            blockHandlerClass = UserSentinel.class, fallback = "fallback")
    public Result createUser(@RequestBody User user) {
        Result result = userService.createUser(user);
        return result;
    }
}
