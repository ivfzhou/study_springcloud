package cn.ivfzhou.reserve_platform.searchserver.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.ivfzhou.reserve_platform.entity.db.Room;
import cn.ivfzhou.reserve_platform.event.EventListener;
import cn.ivfzhou.reserve_platform.event.constant.EventConstant;
import cn.ivfzhou.reserve_platform.searchserver.service.ISearchService;

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
