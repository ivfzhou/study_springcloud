package cn.ivfzhou.springcloud.cityserver.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.ivfzhou.springcloud.cityserver.service.ICityService;
import cn.ivfzhou.springcloud.entity.db.Hotel;
import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;

/**
 * 酒店事件的监听处理器。
 */
@Component
@Slf4j
public class HotelEventListener implements EventListener<Hotel> {

    @Autowired
    private ICityService cityService;

    /**
     * 处理酒店新增事件。
     */
    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_INSERT;
    }

    /**
     * 实际的触发方法。
     */
    @Override
    public void eventHandler(Hotel msg) {
        // 校验参数。
        if (msg == null) return;

        log.info("msg: {}", msg);

        cityService.updateCityNumber(msg.getCid(), 1);
    }

}
