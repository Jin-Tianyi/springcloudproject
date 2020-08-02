package com.module.admin.service;

import com.base.entity.Result;
import com.module.admin.service.hystrix.impl.UserFeignServiceHystrixImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author :jty
 * @date :20-8-1
 * @description : feign 服务调用接口 @FeignClient(value = "user-server")  value 为目标服务名
 * allback = UserFeignServiceHystrixImpl.class Hystrix服务降级方法的实现类
 * 方法需要与UserController定义请求方法一致，即UserController请求接口方法中有的SpringMVC注解，本接口一定要有。
 */
@Component
@FeignClient(value = "user-server",fallback = UserFeignServiceHystrixImpl.class)
public interface UserFeignService {
    /**
     * OpenFeign 超时请求测试
     * @return Result
     */
    @GetMapping(value = "/get/user/timeout")
    Result userServiceTimeOut();

    /**
     * 模拟程序异常
     * @return Result
     */
    @GetMapping(value = "/get/user/exception")
    Result userServiceHappenException();
}
