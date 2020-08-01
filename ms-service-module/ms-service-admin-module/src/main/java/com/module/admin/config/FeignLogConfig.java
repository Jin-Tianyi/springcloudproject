package com.module.admin.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :jty
 * @date :20-8-2
 * @description : 配置feign 日志级别
 */
@Configuration
public class FeignLogConfig {
    /**
     * #日志级别
     * #NONE：默认的，不显示任何日志；
     * #BASIC：仅记录请求方法、URL、响应状态码及执行时间；
     * #HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息；
     * #FULL：除了HEADERS中定义的信息之外，还有请求和响应的正文及元数据。
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
