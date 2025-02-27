package cn.ivfzhou.reserve_platform.common.bloom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class BloomUtil {

    String initScript = "return redis.call('bf.reserve', KEYS[1], ARGV[1], ARGV[2])";

    String addScript = "return redis.call('bf.add', KEYS[1], ARGV[1])";

    String existsScript = "return redis.call('bf.exists', KEYS[1], ARGV[1])";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 初始化布隆过滤器。
     */
    public void initBloom(String key, String error, String initSize) {
        // 执行初始化bloom过滤器的脚本。
        redisTemplate.execute(new DefaultRedisScript<>(initScript, String.class), Collections.singletonList(key), error, initSize);
    }

    /**
     * 添加元素。
     */
    public boolean addBloom(String key, String value) {
        Long result = redisTemplate.execute(new DefaultRedisScript<>(addScript, Long.class), Collections.singletonList(key), value);
        return result != null && result == 1;
    }

    /**
     * 是否存在。
     */
    public boolean isExists(String key, String value) {
        Long result = redisTemplate.execute(new DefaultRedisScript<>(existsScript, Long.class), Collections.singletonList(key), value);
        return result != null && result == 1;
    }

}
