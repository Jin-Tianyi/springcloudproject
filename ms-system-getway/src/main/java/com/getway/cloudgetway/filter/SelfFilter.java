package com.getway.cloudgetway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.awt.*;

/**
 * @author :jty
 * @date :20-8-8
 * @description :
 */
@Component
public class SelfFilter implements GlobalFilter, Ordered {

    private final String URL_KEY = "getway";
    Logger logger = LoggerFactory.getLogger(SelfFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        if (path == null || !path.contains(URL_KEY)) {
            logger.debug("只允许包含getway的请求通过：{}", path);
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return response.setComplete();
        }
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return -1;
    }
}
