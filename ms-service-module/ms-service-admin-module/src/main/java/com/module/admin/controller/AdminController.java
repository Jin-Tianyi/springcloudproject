package com.module.admin.controller;

import com.base.entity.Result;
import com.module.admin.service.UserFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author :jty
 * @date :20-7-28
 * @description : 管理员模块
 */
@RestController
@DefaultProperties(defaultFallback = "userServiceGlobalFb")
public class AdminController {
    @Autowired
    UserFeignService userFeignService;

    /**
     * OpenFeign 超时请求测试
     */
    @GetMapping(value = "/admin/get/user/timeout")
    public Result userServiceTimeOut(){
        Result result = userFeignService.userServiceTimeOut();
        return result;
    }
    /**
     * OpenFeign 模拟请求目的服务器程序异常
     * 注释@HystrixCommand(fallbackMethod = "userServiceExceptionFb") 测试全局服务降级方法
     */
    @GetMapping(value = "/admin/get/user/exception")
    //@HystrixCommand(fallbackMethod = "userServiceExceptionFb")
    @HystrixCommand
    public Result userServiceException(){
        Result result = userFeignService.userServiceTimeOut();
        return result;
    }
    /** 备选响应 */
    public Result userServiceExceptionFb(){
        return new Result(-200,"所请求服务器异常");
    }
    /** 全局备选响应 */
    public Result userServiceGlobalFb(){
        return new Result(-200,"所请求服务器忙或资源不存在");
    }
}
