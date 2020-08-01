package com.module.admin;

import com.module.ribbon.config.RibbonRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author :jty
 * @date :20-7-28
 * @description : @RibbonClient  name:该负载均衡策略生效对象（微服务提供者）微服务名，configuration：自定义配置类
 */
/*@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "user-server",configuration = RibbonRuleConfig.class)
public class AdminApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class);
    }
}*/

/**
 * @author :jty
 * @date :20-7-28
 * @description : 使用自定义负载均衡策略 SelfLoadBalancer.class，去掉@RibbonClient注解
 */
@SpringBootApplication
@EnableEurekaClient
public class AdminApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class);
    }
}

