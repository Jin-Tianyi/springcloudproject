package com.module.user.controller;

import com.base.entity.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


/**
 * @author :jty
 * @date :20-7-20
 * @description :用户模块
 */
@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * OpenFeign 超市请求测试,1.5s超时,自定义处理超时参数
     */
    @GetMapping(value = "/get/user/timeout")
    @HystrixCommand(fallbackMethod = "userServiceTimeOutFb",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
            })
    public Result userServiceTimeOut() {
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }
        return new Result(200, "三秒后处理结束");
    }

    /**
     * 备选响应
     */
    public Result userServiceTimeOutFb() {
        return new Result(-200, "超时请求");
    }

    /**
     * 模拟程序异常
     */
    @GetMapping(value = "/get/user/exception")
    public Result userServiceHappenException() {
        int a = 10 / 0;
        return new Result(200, "程序处理异常");
    }
}
