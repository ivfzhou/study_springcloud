package cn.ivfzhou.springcloud.searchserver.listener;

import cn.ivfzhou.springcloud.entity.db.Room;
import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import cn.ivfzhou.springcloud.searchserver.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelRoomEventListener implements EventListener<Room> {

    @Autowired
    private ISearchService searchService;

    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_ROOM_INSERT;
    }

    @Override
    public void eventHandler(Room room) {
        System.out.println("搜索服务接收到酒店客房新增信息：" + room);
        searchService.insertRoom(room);//保存客房信息
        searchService.insertRoomPrice(room.getHid(), room.getPrices());//保存客房对应的价格信息
    }

}
