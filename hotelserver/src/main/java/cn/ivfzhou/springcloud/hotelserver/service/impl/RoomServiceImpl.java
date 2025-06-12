package cn.ivfzhou.springcloud.hotelserver.service.impl;

import cn.ivfzhou.springcloud.common.util.DateUtil;
import cn.ivfzhou.springcloud.entity.db.Room;
import cn.ivfzhou.springcloud.entity.db.RoomPrice;
import cn.ivfzhou.springcloud.hotelserver.dao.RoomMapper;
import cn.ivfzhou.springcloud.hotelserver.service.IRoomPriceService;
import cn.ivfzhou.springcloud.hotelserver.service.IRoomService;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import cn.ivfzhou.springcloud.rabbitmq.util.EventUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Autowired
    private IRoomPriceService roomPriceService;

    @Autowired
    private EventUtil eventUtil;

    @Override
    @Transactional
    public boolean save(Room room) {

        boolean flag = super.save(room);

        List<RoomPrice> roomPrices = new ArrayList<>();
        room.setPrices(roomPrices);

        //同时生成最近1个月的价格列表
        for (int i = 0; i < 10; i++) {
            RoomPrice roomPrice = new RoomPrice()
                    .setRid(room.getId())
                    .setType(0)//非普通房价
                    .setPrice(room.getDefaultPrice())
                    .setNumber(0)
                    .setHasNumber(room.getNumber())
                    .setDate(DateUtil.getNextDate(i));

            roomPrices.add(roomPrice);
        }

        roomPriceService.saveBatch(roomPrices);

        //发布事件，酒店客房类型和价格新增
        eventUtil.publishEvent(EventConstant.EVENT_HOTEL_ROOM_INSERT, room);

        return flag;
    }

    @Override
    public List<Room> list(Wrapper<Room> queryWrapper) {
        List<Room> rooms = super.list(queryWrapper);
        rooms.forEach(room -> {
            //查询客房价格
            List<RoomPrice> prices = roomPriceService.list(new QueryWrapper<RoomPrice>().eq("rid", room.getId()));
            room.setPrices(prices);
        });
        return rooms;
    }

    @Override
    public Room getById(Serializable id) {
        Room room = super.getById(id);
        //查询客房价格
        List<RoomPrice> prices = roomPriceService.list(new QueryWrapper<RoomPrice>().eq("rid", room.getId()));
        room.setPrices(prices);
        return room;
    }

}
