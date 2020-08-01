package com.module.user;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author :jty
 * @date :20-7-20
 * @description :用户模块   @EnableEurekaClient eureka客户端 @EnableDiscoveryClient开启服务发现，获取服务信息
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class UserApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(UserApp.class).web(WebApplicationType.SERVLET).run(args);
    }
}
