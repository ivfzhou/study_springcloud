package cn.ivfzhou.springcloud.hotelserver.service;

import cn.ivfzhou.springcloud.entity.db.RoomPrice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

public interface IRoomPriceService extends IService<RoomPrice> {

    int updateRoomNumber(int rid, int number, Date beginTime, Date endTime);

}
