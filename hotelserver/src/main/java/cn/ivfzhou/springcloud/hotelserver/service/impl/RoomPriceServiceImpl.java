package cn.ivfzhou.springcloud.hotelserver.service.impl;

import cn.ivfzhou.springcloud.entity.db.RoomPrice;
import cn.ivfzhou.springcloud.hotelserver.dao.RoomPriceMapper;
import cn.ivfzhou.springcloud.hotelserver.service.IRoomPriceService;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import cn.ivfzhou.springcloud.rabbitmq.util.EventUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RoomPriceServiceImpl extends ServiceImpl<RoomPriceMapper, RoomPrice> implements IRoomPriceService {

    @Autowired
    private RoomPriceMapper roomPriceMapper;

    @Autowired
    private EventUtil eventUtil;

    /**
     * 修改了客房对象信息 - 调整了价格
     */
    @Override
    public boolean updateById(RoomPrice entity) {
        boolean flag = super.updateById(entity);
        //发送一个事件 - 修改了客房价格对象
        eventUtil.publishEvent(EventConstant.EVENT_HOTEL_PRICE_UPDATE, entity);
        return flag;
    }

    /**
     * 修改rid对应的房间类型的剩余数量
     */
    @Override
    public int updateRoomNumber(int rid, int number, Date beginTime, Date endTime) {
        return roomPriceMapper.updateRoomNumber(rid, number, beginTime, endTime);
    }

}
