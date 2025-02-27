package cn.ivfzhou.reserve_platform.gatewayserver.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.err.println("全局过滤器生效！！！");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 200;
    }

}
