package cn.ivfzhou.springcloud.gatewayserver.filter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson2.JSON;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import cn.ivfzhou.springcloud.common.constant.ResultDataCode;
import cn.ivfzhou.springcloud.common.util.TokenBucket;
import cn.ivfzhou.springcloud.entity.ResultData;

/**
 * 限流过滤器。
 */
@Component
public class RedisLimiterFilter implements GatewayFilter, Ordered {

    //令牌桶的集合。
    private final Map<String, TokenBucket> bucketMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 根据访问的 URL 限流。
        String reqPath = exchange.getRequest().getPath().value();

        // 生成令牌桶。
        // 每个 URL 对应一个桶。
        // 一个 URL 只有第一次需要创建桶，否则直接获取对应的桶即可。
        TokenBucket bucket = bucketMap.computeIfAbsent(reqPath, s -> new TokenBucket(s, 500, 500));

        // 从桶中获取令牌。
        boolean flag = bucket.tryReserveToken(1);
        if (flag) {
            // 已经过令牌。
            return chain.filter(exchange);
        }

        // 未获得令牌。
        ResultData<String> resultData = new ResultData<String>().setCode(ResultDataCode.RARE_LIMIT_REACHED)
                .setMsg("请求限流");
        ServerHttpResponse response = exchange.getResponse();
        // 将回写的数据转换成 dataBuffer 对象。
        DataBuffer dataBuffer = response.bufferFactory()
                .wrap(JSON.toJSONString(resultData).getBytes());
        // 设置响应头告诉客户端，返回的是一个 JSON。
        response.getHeaders().put("Content-Type", Collections.singletonList("application/json"));
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return 2;
    }

}
