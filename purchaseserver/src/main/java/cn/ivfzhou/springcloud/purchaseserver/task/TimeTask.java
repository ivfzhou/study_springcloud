package cn.ivfzhou.springcloud.purchaseserver.task;

import cn.ivfzhou.springcloud.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务组件。
 * <p>
 * 定时更新 Redis 中的当前抢购场次时间，每两小时执行一次，
 * 用于前端判断当前可参与的抢购场次。
 * </p>
 */
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
