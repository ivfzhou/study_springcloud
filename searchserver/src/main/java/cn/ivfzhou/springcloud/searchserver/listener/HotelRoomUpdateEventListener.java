package cn.ivfzhou.springcloud.searchserver.listener;

import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;
import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import cn.ivfzhou.springcloud.searchserver.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelRoomUpdateEventListener implements EventListener<OrderPriceParam> {

    @Autowired
    private ISearchService searchService;

    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_ROOM_UPDATE;
    }

    @Override
    public void eventHandler(OrderPriceParam msg) {
        System.out.println("修改搜索服务对应的酒店下单数量");
        searchService.updateRoomNumber(msg.getHid(), msg.getRid(), msg.getRnumber(), msg.getBeginTime(), msg.getEndTime());
    }

}
