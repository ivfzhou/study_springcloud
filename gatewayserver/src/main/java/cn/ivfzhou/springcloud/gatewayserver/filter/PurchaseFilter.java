package cn.ivfzhou.springcloud.gatewayserver.filter;

import java.util.Collections;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import cn.ivfzhou.springcloud.common.constant.RedisKey;
import cn.ivfzhou.springcloud.entity.ResultData;

/**
 * 防止抢劵时提前下单的局部过滤器。
 */
@Component
public class PurchaseFilter implements GatewayFilter, Ordered {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 过滤的核心处理方法。
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获得优惠券 ID 的参数。
        String cid = exchange.getRequest().getQueryParams().getFirst("cid");
        if (!StringUtils.isEmpty(cid)) {
            // 防止提前下单。
            String nowTime = redisTemplate.opsForValue().get("now_time");
            // 查询对应场次的优惠券集合。
            Boolean flag = redisTemplate.opsForSet().isMember(String.format(RedisKey.COUPON_FMT_SET, nowTime), cid);
            if (Boolean.TRUE.equals(flag)) {
                // 优惠券已经可以开抢。当前秒杀场次包含该优惠券。
                return chain.filter(exchange);
            }
        }

        // 该优惠券不能抢购。可能是提前下单，非法请求。
        ResultData<Integer> resultData = new ResultData<Integer>().setData(-1);
        ServerHttpResponse response = exchange.getResponse();
        // 将回写的数据转换成 dataBuffer 对象。
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(resultData).getBytes());
        // 设置响应头告诉客户端，返回的是一个 JSON。
        response.getHeaders().put("Content-Type", Collections.singletonList("application/json"));
        return response.writeWith(Mono.just(dataBuffer));
    }

    /**
     * 当前过滤器的顺序，值越小，优先级越大，可以是负数。
     */
    @Override
    public int getOrder() {
        return 3;
    }

}
