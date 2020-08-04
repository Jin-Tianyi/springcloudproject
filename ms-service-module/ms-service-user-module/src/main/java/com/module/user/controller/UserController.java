package com.module.user.controller;

import com.base.entity.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * @author :jty
 * @date :20-7-20
 * @description :用户模块
 */
@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static int index = 0;

    /**
     * OpenFeign 超市请求测试,1.5s超时,自定义处理超时参数
     */
    @GetMapping(value = "/get/user/timeout")
    public Result userServiceTimeOut() {
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }
        return new Result(200, "三秒后处理结束");
    }

    /**
     * 模拟程序异常 ，服务熔断实例
     */
    @GetMapping(value = "/get/user/exception/{number}")
    @HystrixCommand(fallbackMethod = "userServiceHappenExceptionFb", commandProperties = {
            //开启熔断
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //时间窗口请求数量
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            //时间窗口持续10s时间
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
            //达到60%错误率后开启熔断器
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
            //6s后进入半开状态
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "6000")
    })
    public Result userServiceHappenException(@PathVariable(value = "number") int number) {
        logger.info("---------正常执行--------");
        int a = 10 / number;
        Date date = new Date();
        logger.info("请求时间：{},请求序列：{}", df.format(date), index++);
        return new Result(200, "程序正常结束", a);
    }

    public Result userServiceHappenExceptionFb(@PathVariable(value = "number") int number) {
        logger.info("---------执行fallback方法--------");
        Date date = new Date();
        logger.info("请求时间：{},请求序列：{}", df.format(date), index++);
        return new Result(200, "程序异常中断");
    }

}
