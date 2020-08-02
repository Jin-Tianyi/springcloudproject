package com.module.admin.service.hystrix.impl;

import com.base.entity.Result;
import com.module.admin.service.UserFeignService;

/**
 * @author :jty
 * @date :20-8-2
 * @description :
 */
public class UserFeignServiceHystrixImpl implements UserFeignService {
    @Override
    public Result userServiceTimeOut() {
        return new Result(-200,"服务端");
    }

    @Override
    public Result userServiceHappenException() {
        return null;
    }
}
