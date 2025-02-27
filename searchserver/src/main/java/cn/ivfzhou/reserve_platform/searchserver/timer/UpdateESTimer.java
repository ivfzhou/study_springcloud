package cn.ivfzhou.reserve_platform.searchserver.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

import cn.ivfzhou.reserve_platform.searchserver.service.ISearchService;
import cn.ivfzhou.reserve_platform.common.lock.LockUtil;

/**
 * 定时器 - 10秒触发一次
 */
@Component
public class UpdateESTimer {

    @Autowired
    private ISearchService searchService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private LockUtil lockUtil;

    /**
     * 定时任务 - 按照一定频率将redis中的点击率更新到elasticsearch中
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void myTask() {

        lockUtil.lock("djllock");

        try {
            // 从redis中读取各酒店点击率，更新到elasticsearch中。
            Set<Object> hidList = redisTemplate.opsForHash().keys("djl_cache");
            hidList.forEach(o -> {
                //获得当前的酒店id
                Integer hid = Integer.parseInt(String.valueOf(o));
                //获得酒店对应的点击数量
                Integer djl = Integer.valueOf("" + redisTemplate.opsForHash().get("djl_cache", hid + ""));

                //更新到es中
                searchService.updateHotelDjl(hid, djl);

                //删除对应酒店的点击率
                redisTemplate.opsForHash().delete("djl_cache", hid + "");
            });
        } finally {
            //解锁
            lockUtil.unlock("djllock");
        }
    }

}
