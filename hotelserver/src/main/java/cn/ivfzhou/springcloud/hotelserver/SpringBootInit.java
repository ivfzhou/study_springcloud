package cn.ivfzhou.springcloud.hotelserver;

import cn.ivfzhou.springcloud.common.util.BloomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SpringBootInit implements CommandLineRunner {

    @Autowired
    private BloomUtil bloomUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) throws Exception {
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey("djl"))) {
            // 初始化布隆过滤器。
            bloomUtil.init("djl", "0.001", "10000000");
        }
    }

}
