package com.module.admin.lb;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author :jty
 * @date :20-8-1
 * @description :自定义轮询负载均衡策略
 */
public class SelfRoundRobinRule extends AbstractLoadBalancerRule {
    private AtomicInteger nextServerCyclicCounter;
    private static final boolean AVAILABLE_ONLY_SERVERS = true;
    private static final boolean ALL_SERVERS = false;
    /**
     * 每个请求轮询两次
     */
    private static final int ROUND_NUMBER = 2;
    private static Logger log = LoggerFactory.getLogger(SelfRoundRobinRule.class);

    public SelfRoundRobinRule() {
        this.nextServerCyclicCounter = new AtomicInteger(0);
    }

    public SelfRoundRobinRule(ILoadBalancer lb) {
        this();
        this.setLoadBalancer(lb);
    }

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        } else {
            Server server = null;
            int count = 0;

            while (true) {
                if (server == null && count++ < 10) {
                    List<Server> reachableServers = lb.getReachableServers();
                    List<Server> allServers = lb.getAllServers();
                    int upCount = reachableServers.size();
                    int serverCount = allServers.size();
                    if (upCount != 0 && serverCount != 0) {
                        int nextServerIndex = this.incrementAndGetModulo(serverCount);
                        server = (Server) allServers.get(nextServerIndex);
                        if (server == null) {
                            Thread.yield();
                        } else {
                            if (server.isAlive() && server.isReadyToServe()) {
                                return server;
                            }

                            server = null;
                        }
                        continue;
                    }

                    log.warn("No up servers available from load balancer: " + lb);
                    return null;
                }

                if (count >= 10) {
                    log.warn("No available alive servers after 10 tries from load balancer: " + lb);
                }

                return server;
            }
        }
    }

    private int incrementAndGetModulo(int modulo) {
        int current;
        int next;
        int index;
        do {
            current = this.nextServerCyclicCounter.get();
            log.info("当前请求{}次", current);
            //下次请求次数大于等于Integer.MAX_VALUE时，重置为0
            next = (current + 1) >= 2147483647 ? 0 : (current + 1);
        } while (!this.nextServerCyclicCounter.compareAndSet(current, next));

        //轮询两次 下一次请求下标=(次数/2)/总服务数量
        index = (current / ROUND_NUMBER) % modulo;
        log.info("下次请求下标为：{}", index);
        return index;
    }

    @Override
    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}
