package cn.ivfzhou.springcloud.searchserver.listener;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.RoomPrice;
import cn.ivfzhou.springcloud.feign.HotelFeign;
import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import cn.ivfzhou.springcloud.searchserver.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
