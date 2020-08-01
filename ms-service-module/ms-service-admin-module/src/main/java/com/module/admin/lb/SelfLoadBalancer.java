package com.module.admin.lb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author :jty
 * @date :20-8-1
 * @description :自定义负载均衡策略，选择目标服务的调用地址
 */
@Component
public class SelfLoadBalancer {
    private static Logger log = LoggerFactory.getLogger(SelfRoundRobinRule.class);

    public final ServiceInstance chooseByRandomRule(List<ServiceInstance> serviceInstances) {
        log.info("----------使用自定义随机策略----------");
        ServiceInstance instance = null;
        int instanceCount = serviceInstances.size();
        while (instance == null) {
            if (Thread.interrupted()) {
                return null;
            }

            int index = this.chooseRandomInt(instanceCount);
            log.info("下次请求下标为：{}", index);
            instance = (ServiceInstance) serviceInstances.get(index);
            Thread.yield();
        }
        return instance;

    }

    protected int chooseRandomInt(int serverCount) {
        return ThreadLocalRandom.current().nextInt(serverCount);
    }
}
