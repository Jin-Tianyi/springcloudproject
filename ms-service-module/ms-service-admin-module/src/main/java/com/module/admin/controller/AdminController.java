package com.module.admin.controller;

import com.base.dao.User;
import com.base.entity.Result;
import com.module.admin.lb.SelfLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author :jty
 * @date :20-7-28
 * @description : 管理员模块
 */
@RestController
public class AdminController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    SelfLoadBalancer selfLoadBalancer;
    /**
     * 服务名
     */
    private final String SERVICE_NAME = "user-server";
    /**
     * 通过服务名调用
     */
    private static final String USER_MODULE_URL = "http://user-server";

    /**
     * RestTemplate forObject
     */
    @GetMapping(value = "/admin/get/userObject/{userId}", produces = "application/json;charset=utf-8")
    public Result searchUser(@PathVariable int userId) {
        Result result = restTemplate.getForObject(USER_MODULE_URL + "/get/user/" + userId, Result.class);
        return result;
    }

    /**
     * RestTemplate forEntity
     */
    @GetMapping(value = "/admin/get/userEntity/{userId}", produces = "application/json;charset=utf-8")
    public Result findUser(@PathVariable int userId) {
        ResponseEntity<Result> entity = restTemplate.getForEntity(USER_MODULE_URL + "/get/user/" + userId, Result.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new Result(-200, "请求失败！");
        }
    }

    /**
     * 自定义负载均衡策略选择目标服务实例地址
     */
    @GetMapping(value = "/admin/get/user/{userId}", produces = "application/json;charset=utf-8")
    public Result selfRuleFindUser(@PathVariable int userId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(SERVICE_NAME);
        ServiceInstance serviceInstance = selfLoadBalancer.chooseByRandomRule(instances);
        Result result = restTemplate.getForObject(serviceInstance.getUri() + "/get/user/" + userId, Result.class);
        result.setMsg("自定义随机数负载均衡策略----------" + result.getMsg());
        return result;
    }

    @GetMapping(value = "/admin/post/create/user", produces = "application/json;charset=utf-8")
    public Result createUser(@RequestBody User user) {
        Result result = restTemplate.postForObject(USER_MODULE_URL + "/post/create/user", user, Result.class);
        return result;
    }
}
