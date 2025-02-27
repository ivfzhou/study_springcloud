package cn.ivfzhou.reserve_platform.purchaseserver.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import cn.ivfzhou.reserve_platform.common.util.DateUtil;

@Component
public class TimeTask {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0 0 0/2 * * *")
    public void couponTask() {
        // 抢购场 12 14 16....。
        // 更新redis的抢购的场次时间。
        stringRedisTemplate.opsForValue().set("now_time", DateUtil.date2Str(new Date(), "yyMMddHH"));
    }

}
