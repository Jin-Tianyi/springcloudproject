package com.module.admin.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author :jty
 * @date :20-7-28
 * @description :注入RestTemplate Bean
 */
@Configuration
public class RestTemplateConfig {
    /**
     * @LoadBalanced 通过服务名调用，开启负载均衡
     * 使用自定义负载均衡方法时需注释该方法，不开启服务名调用
     * */
    /*@Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }*/

    /**
     * @LoadBalanced 直接使用RestTemplate调用请求地址，使用自定义负载均衡方法获取地址
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
