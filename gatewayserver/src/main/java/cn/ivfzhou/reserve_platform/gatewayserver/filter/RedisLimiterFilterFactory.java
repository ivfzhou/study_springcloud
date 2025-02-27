package cn.ivfzhou.reserve_platform.gatewayserver.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisLimiterFilterFactory implements GatewayFilterFactory<RedisLimiterFilter> {

    @Autowired
    private RedisLimiterFilter redisLimiterFilter;

    @Override
    public Class<RedisLimiterFilter> getConfigClass() {
        return RedisLimiterFilter.class;
    }

    @Override
    public GatewayFilter apply(RedisLimiterFilter config) {
        return redisLimiterFilter;
    }

    @Override
    public String name() {
        return "redisLimiter";
    }

}
