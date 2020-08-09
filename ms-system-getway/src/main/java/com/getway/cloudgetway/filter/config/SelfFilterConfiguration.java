package com.getway.cloudgetway.filter.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author :jty
 * @date :20-8-9
 * @description :
 */
@Configuration
public class SelfFilterConfiguration {
    private final String MODULE_NAME = SelfFilterConfiguration.class.getName() + ".admin-module";

    /**
     * @Order 注解无效
     */
    @Bean
    @Order(-100)
    public GlobalFilter RewritePathFilter() {
        return (exchange, chain) -> {
            exchange.getAttributes().put(MODULE_NAME, "admin-module");
            return chain.filter(exchange).then(

            );
        };
    }
}
