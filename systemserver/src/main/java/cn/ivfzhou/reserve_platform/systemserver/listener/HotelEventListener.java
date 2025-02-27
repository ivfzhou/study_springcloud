package cn.ivfzhou.reserve_platform.systemserver.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import cn.ivfzhou.reserve_platform.entity.db.Hotel;
import cn.ivfzhou.reserve_platform.event.EventListener;
import cn.ivfzhou.reserve_platform.event.constant.EventConstant;

@Component
@Slf4j
public class HotelEventListener implements EventListener<Hotel> {

    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_INSERT;
    }

    @Override
    public void eventHandler(Hotel msg) {
        log.info("系统服务，接收到酒店新增的消息：" + msg);
    }

}
