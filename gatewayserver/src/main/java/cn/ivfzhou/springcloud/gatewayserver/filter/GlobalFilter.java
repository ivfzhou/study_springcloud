package cn.ivfzhou.springcloud.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局网关过滤器。
 * <p>
 * 对所有经过网关的请求进行统一的处理，
 * 当前主要记录请求的进入和离开日志，便于链路追踪和问题排查。
 * 过滤器优先级为 1。
 * </p>
 */
public class GlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(GlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            log.info("全局过滤器开始");
            return chain.filter(exchange);
        } finally {
            log.info("全局过滤器结束");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
