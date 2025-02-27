package cn.ivfzhou.reserve_platform.gatewayserver.filter;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.gatewayserver.util.TokenBucket;

/**
 * 限流过滤器。
 */
@Component
public class RedisLimiterFilter implements GatewayFilter, Ordered {

    //令牌桶的集合
    private final Map<String, TokenBucket> bucketMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 根据访问的URL限流。
        String url = exchange.getRequest().getPath().value();

        // 生成令牌桶。
        // 每个url对应一个桶。
        // 一个url只有第一次需要创建桶，否则直接获取对应的桶即可。
        TokenBucket bucket = bucketMap.computeIfAbsent(url, s -> new TokenBucket(s, 500, 500));

        // 从桶中获取令牌。
        boolean flag = bucket.tryReserveToken(1);
        if (flag) {
            // 已经过的令牌。
            return chain.filter(exchange);
        }

        // 未获得令牌。
        // 未获得令。
        // 回写的对。
        ResultData<String> resultData = new ResultData<String>().setCode(ResultData.Code.MUST_REQUEST).setMsg("服务器繁忙，请稍后再试！");
        ServerHttpResponse response = exchange.getResponse();
        // 将回写的数据转换成dataBuffer对象。
        DataBuffer dataBuffer = response.bufferFactory()
                .wrap(JSON.toJSONString(resultData).getBytes());
        // 设置响应头告诉客户端，返回的是一个json。
        response.getHeaders().put("Content-Type", Collections.singletonList("application/json"));
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return -1000;
    }

}
