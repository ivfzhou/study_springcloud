package cn.ivfzhou.reserve_platform.hotelserver.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.ivfzhou.reserve_platform.entity.db.OrderPriceParam;
import cn.ivfzhou.reserve_platform.event.EventListener;
import cn.ivfzhou.reserve_platform.event.constant.EventConstant;
import cn.ivfzhou.reserve_platform.hotelserver.service.IRoomPriceService;

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
