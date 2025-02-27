package cn.ivfzhou.reserve_platform.cityserver.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import cn.ivfzhou.reserve_platform.entity.db.Hotel;
import cn.ivfzhou.reserve_platform.event.EventListener;
import cn.ivfzhou.reserve_platform.event.constant.EventConstant;
import cn.ivfzhou.reserve_platform.cityserver.service.ICityService;

/**
 * 酒店事件的监听处理器。
 */
@Component
@Slf4j
public class HotelEventListener implements EventListener<Hotel> {

    @Autowired
    private ICityService cityService;

    /**
     * 处理"酒店新增"事件。
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
        log.info("城市服务，接收到酒店新增事件，{}", msg);
        cityService.updateCityNumber(msg.getCid(), 1);
    }

}
