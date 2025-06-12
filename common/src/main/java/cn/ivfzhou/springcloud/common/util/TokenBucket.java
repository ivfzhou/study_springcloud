package cn.ivfzhou.springcloud.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * 令牌桶。
 * TokenBucket 对象就是一个独立令牌桶。
 */
@Data
@Slf4j
@Accessors(chain = true)
public class TokenBucket {

    // 获得令牌的 Lua 脚本。
    String getTokenLua = "-- 令牌桶的key\n" +
            "local key = 'tokentong_'..KEYS[1]\n" +
            "-- 需要多少令牌\n" +
            "local getTokens = tonumber(ARGV[1])\n" +
            "-- 允许等待的最大时间（超时时间）微秒\n" +
            "local timeouts = tonumber(ARGV[2] or -1)\n" +
            "-- 获得令牌桶中的参数\n" +
            "-- 令牌桶中拥有的令牌\n" +
            "local hasToken = tonumber(redis.call('hget', key, 'hasToken'))\n" +
            "-- 令牌桶的最大令牌数\n" +
            "local maxToken = tonumber(redis.call('hget', key, 'maxToken'))\n" +
            "-- 每秒产生的令牌数\n" +
            "local tokensSec = tonumber(redis.call('hget', key, 'tokensSec'))\n" +
            "-- 下一次可以计算生成令牌的时间（微秒）\n" +
            "local nextTimes = tonumber(redis.call('hget', key, 'nextTimes'))\n" +
            "-- 进行一些参数计算\n" +
            "-- 计算多久（微秒）产生一个令牌\n" +
            "local oneTokenTimes = 1000000/tokensSec\n" +
            "-- 获取当前时间\n" +
            "local now = redis.call('time')\n" +
            "-- 计算当前微秒的时间戳\n" +
            "local nowTimes = tonumber(now[1]) * 1000000 + tonumber(now[2])\n" +
            "-- 判断超时时间范围内时候能够成功预支到令牌，如果不行，直接返回-1\n" +
            "-- 如果下一次计算令牌的时间，比当前时间要大，就需要等待，就有可能超过过期时间\n" +
            "if timeouts ~= -1 then\n" +
            "    -- 在过期时间内，无法获得令牌\n" +
            "    if nextTimes - nowTimes > timeouts then\n" +
            "        return -1\n" +
            "    end\n" +
            "end\n" +
            "-- 生成令牌\n" +
            "if nowTimes > nextTimes then\n" +
            "   -- 计算生成的令牌数\n" +
            "   local createTokens = (nowTimes - nextTimes) / oneTokenTimes\n" +
            "   -- 计算拥有的令牌数\n" +
            "   hasToken = math.min(createTokens + hasToken, maxToken)\n" +
            "   -- 更新下一次可以计算令牌的时间\n" +
            "   nextTimes = nowTimes\n" +
            "end\n" +
            "-- 获取令牌\n" +
            "-- 当前能够拿到的令牌数量\n" +
            "local canTokens = math.min(getTokens, hasToken)\n" +
            "-- 需要预支的令牌数量\n" +
            "local reserveTokens = getTokens - canTokens\n" +
            "-- 根据预支的令牌数，计算需要预支多少时间（微秒）\n" +
            "local reserveTimes = reserveTokens * oneTokenTimes\n" +
            "-- 更新下一次可以计算令牌的时间\n" +
            "nextTimes = nextTimes + reserveTimes\n" +
            "-- 更新当前剩余的令牌\n" +
            "hasToken = hasToken - canTokens\n" +
            "-- 更新redis\n" +
            "redis.call('hmset', key, 'hasToken', hasToken, 'nextTimes', nextTimes)\n" +
            "-- 返回本次获取令牌需要等待的时间\n" +
            "return math.max(nextTimes - nowTimes, 0)";

    private StringRedisTemplate redisTemplate;

    // 桶的名称。
    private String key;

    // 最大的令牌数量。
    private int maxToken;

    // 每秒生成多少令牌（决定了令牌的生成速率）。
    private int tokensSec;

    public TokenBucket(String key, int maxToken, int tokensSec) {
        this.key = key;
        this.maxToken = maxToken;
        this.tokensSec = tokensSec;
        this.redisTemplate = SpringBeanUtil.getBean(StringRedisTemplate.class);
        // 初始化令牌桶。
        init();
    }


    // 初始化令牌桶，redis 中 hash 结构。
    private void init() {
        Map<String, String> values = new HashMap<>();
        // 当前令牌桶的容量。
        values.put("hasToken", maxToken + "");
        // 最大的令牌容器。
        values.put("maxToken", maxToken + "");
        // 每秒产生令牌的数量。
        values.put("tokensSec", tokensSec + "");
        // 下一次可以计算令牌的时间。
        values.put("nextTimes", TimeUnit.MILLISECONDS.toMicros(System.currentTimeMillis()) + "");
        redisTemplate.opsForHash().putAll("tokentong_" + key, values);
    }

    // 尝试去获取 tokens 个令牌，返回值表示获取这些令牌，需要等待的时间，如果返回 0 表示无需等待。
    public double reserveToken(int tokens) {
        // 执行脚本 - 返回需要等待的微秒数。
        Long waitMicro = redisTemplate.execute(
                new DefaultRedisScript<>(getTokenLua, Long.class),
                Collections.singletonList(key),
                tokens + ""
        );
        if (waitMicro > 0) {
            // 等待时间。
            try {
                Thread.sleep(waitMicro / 1000);
            } catch (InterruptedException e) {
                log.error("等待令牌桶时间出错", e);
            }
        }

        return waitMicro;
    }

    // 尝试预约 tokens 令牌，如果在 timeout 时间范围内，可以预约到，就返回 true（需要等待预约的时间）。
    // 如果发现 timeout 时间范围内，没办法预约到当前令牌，直接返回 false，表示令牌获取失败（无需等待）。
    public boolean tryReserveToken(int tokens, int timeout, TimeUnit unit) {
        // 执行脚本 - 返回需要等待的微秒数。
        Long waitMicro = redisTemplate.execute(
                new DefaultRedisScript<>(getTokenLua, Long.class),
                Collections.singletonList(key),
                tokens + "",
                unit.toMicros(timeout) + ""
        );

        if (waitMicro == -1) {
            return false;
        } else if (waitMicro > 0) {
            // 需要等待。
            try {
                Thread.sleep(waitMicro / 1000);
            } catch (InterruptedException e) {
                log.error("等待令牌桶时间出错", e);
            }
        }
        // 可以获得令牌数。
        return true;
    }

    // 直接获取指定数量的令牌，如果可以直接拿到，返回 true，如果不能直接拿，直接返回 false。
    public boolean tryReserveToken(int tokens) {
        return tryReserveToken(tokens, 0, TimeUnit.MICROSECONDS);
    }

}
