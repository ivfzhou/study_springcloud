package cn.ivfzhou.springcloud.systemserver.listener;

import cn.ivfzhou.springcloud.entity.db.Hotel;
import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
