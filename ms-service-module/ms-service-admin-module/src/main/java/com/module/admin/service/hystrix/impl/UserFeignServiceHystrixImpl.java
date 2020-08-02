package com.module.admin.service.hystrix.impl;

import com.base.entity.Result;
import com.module.admin.service.UserFeignService;
import org.springframework.stereotype.Component;

/**
 * @author :jty
 * @date :20-8-2
 * @description : 通过实现feign接口使用Hystrix服务降级
 */
@Component
public class UserFeignServiceHystrixImpl implements UserFeignService {
    @Override
    public Result userServiceTimeOut() {
        return new Result(-200,UserFeignServiceHystrixImpl.class.getName()+"服务器请求超时或已宕机");
    }

    @Override
    public Result userServiceHappenException() {
        return new Result(-200,UserFeignServiceHystrixImpl.class.getName()+"服务器异常或已宕机");
    }
}
