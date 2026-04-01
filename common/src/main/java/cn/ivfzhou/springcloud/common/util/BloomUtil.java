package cn.ivfzhou.springcloud.common.util;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

/**
 * Redis 布隆过滤器工具类。
 * <p>
 * 基于 RedisBloom 模块提供布隆过滤器的初始化、元素添加和存在性判断功能。
 * 使用 Lua 脚本通过 Redis 执行布隆过滤器操作，适用于优惠券抢购等场景的快速判重。
 * </p>
 */
@Component
public class BloomUtil {

    /** 初始化布隆过滤器的 Lua 脚本 */
    String initScript = "return redis.call('bf.reserve', KEYS[1], ARGV[1], ARGV[2])";

    /** 向布隆过滤器添加元素的 Lua 脚本 */
    String addScript = "return redis.call('bf.add', KEYS[1], ARGV[1])";

    /** 判断元素是否存在于布隆过滤器的 Lua 脚本 */
    String existsScript = "return redis.call('bf.exists', KEYS[1], ARGV[1])";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 初始化布隆过滤器。
     */
    public void init(String key, String error, String initSize) {
        // 执行初始化 bloom 过滤器的脚本。
        redisTemplate.execute(new DefaultRedisScript<>(initScript, String.class), Collections.singletonList(key), error, initSize);
    }

    /**
     * 添加元素。
     */
    public boolean add(String key, String value) {
        Long result = redisTemplate.execute(new DefaultRedisScript<>(addScript, Long.class), Collections.singletonList(key), value);
        return result != null && result == 1;
    }

    /**
     * 是否存在。
     */
    public boolean exist(String key, String value) {
        Long result = redisTemplate.execute(new DefaultRedisScript<>(existsScript, Long.class), Collections.singletonList(key), value);
        return result != null && result == 1;
    }

}
