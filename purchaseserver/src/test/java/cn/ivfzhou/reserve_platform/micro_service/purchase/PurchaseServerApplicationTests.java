package cn.ivfzhou.reserve_platform.micro_service.purchase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class PurchaseServerApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void contextLoads() {

        // 流水线插入数据。
        redisTemplate.executePipelined(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                // 流水线操作redis。
                for (int i = 0; i < 200000; i++) {
                    redisOperations.opsForSet().add("coupon_20072116", i + "");
                }

                return null;
            }
        });
    }

}
