package cn.ivfzhou.reserve_platform.hotelserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

import cn.ivfzhou.reserve_platform.entity.db.RoomPrice;

public interface IRoomPriceService extends IService<RoomPrice> {

    int updateRoomNumber(int rid, int number, Date beginTime, Date endTime);

}
