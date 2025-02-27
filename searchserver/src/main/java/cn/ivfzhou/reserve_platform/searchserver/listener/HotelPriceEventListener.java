package cn.ivfzhou.reserve_platform.searchserver.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.entity.db.RoomPrice;
import cn.ivfzhou.reserve_platform.event.EventListener;
import cn.ivfzhou.reserve_platform.event.constant.EventConstant;
import cn.ivfzhou.reserve_platform.feign.HotelFeign;
import cn.ivfzhou.reserve_platform.searchserver.service.ISearchService;

@Component
public class HotelPriceEventListener implements EventListener<RoomPrice> {

    @Autowired
    private ISearchService searchService;

    @Autowired
    private HotelFeign hotalFeign;

    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_PRICE_UPDATE;
    }

    @Override
    public void eventHandler(RoomPrice roomPrice) {
        System.out.println("搜索服务接收到酒店价格更新事件，" + roomPrice);
        ResultData<Integer> hidData = hotalFeign.getHotelIdByRoomId(roomPrice.getRid());
        searchService.updateRoomPrice(hidData.getData(), roomPrice);
    }

}
