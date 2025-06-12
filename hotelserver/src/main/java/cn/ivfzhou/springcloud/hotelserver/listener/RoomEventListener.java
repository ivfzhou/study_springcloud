package cn.ivfzhou.springcloud.hotelserver.listener;

import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;
import cn.ivfzhou.springcloud.hotelserver.service.IRoomPriceService;
import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理酒店下单数量改变的事件
 */
@Component
public class RoomEventListener implements EventListener<OrderPriceParam> {

    @Autowired
    private IRoomPriceService roomPriceService;

    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_ROOM_UPDATE;
    }

    @Override
    public void eventHandler(OrderPriceParam msg) {
        System.out.println("修改对应的酒店下单数量");
        roomPriceService.updateRoomNumber(msg.getRid(), msg.getRnumber(), msg.getBeginTime(), msg.getEndTime());
    }

}
