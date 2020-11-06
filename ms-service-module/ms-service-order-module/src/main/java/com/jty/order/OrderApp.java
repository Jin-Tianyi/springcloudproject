package com.jty.order;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author :jty
 * @date :20-9-10
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OrderApp.class).web(WebApplicationType.SERVLET).run(args);
    }
    /**
     * @LoadBalanced 通过服务名调用，开启Ribbon负载均衡,默认轮询
     * */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
