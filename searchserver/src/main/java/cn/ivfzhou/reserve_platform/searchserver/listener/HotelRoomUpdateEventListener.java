package cn.ivfzhou.reserve_platform.searchserver.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.ivfzhou.reserve_platform.entity.db.OrderPriceParam;
import cn.ivfzhou.reserve_platform.event.EventListener;
import cn.ivfzhou.reserve_platform.event.constant.EventConstant;
import cn.ivfzhou.reserve_platform.searchserver.service.ISearchService;

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
