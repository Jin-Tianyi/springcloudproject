package com.module.ribbon.config;

import com.module.admin.lb.SelfRoundRobinRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :jty
 * @date :20-7-31
 * @description :注入随机策略
 */
@Configuration
public class RibbonRuleConfig {
    @Bean
    public IRule getRule(){
        return new RandomRule();
    }
}

/**
 * @author :jty
 * @date :20-7-31
 * @description :注入自定义轮询策略
 */
/*@Configuration
public class RibbonRuleConfig {
    @Bean
    public IRule getRule(){
        return new SelfRoundRobinRule();
    }
}*/
