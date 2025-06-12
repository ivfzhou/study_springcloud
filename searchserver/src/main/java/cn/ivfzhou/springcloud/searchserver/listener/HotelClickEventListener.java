package cn.ivfzhou.springcloud.searchserver.listener;

import cn.ivfzhou.springcloud.entity.db.Hotel;
import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 酒店点击事件。
 */
@Component
public class HotelClickEventListener implements EventListener<Hotel> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_CLICK;
    }

    @Override
    public void eventHandler(Hotel hotal) {
        // 将点击量保存到redis中。
        // hash   djl2 - hid：xxxx。
        Boolean flag = redisTemplate.opsForHash().hasKey("djl_cache", hotal.getId() + "");
        if (flag) {
            //存在
            redisTemplate.opsForHash().increment("djl_cache", hotal.getId() + "", 1);
        } else {
            //不存在
            redisTemplate.opsForHash().put("djl_cache", hotal.getId() + "", "1");
        }
    }

}
