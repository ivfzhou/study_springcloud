package cn.ivfzhou.springcloud.hotelserver.service;

import cn.ivfzhou.springcloud.entity.db.RoomPrice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * 客房价格业务服务接口。
 * <p>
 * 继承 MyBatis-Plus 的 IService，提供客房价格实体（RoomPrice）的基础业务操作，
 * 并扩展了按日期范围更新剩余数量的方法。
 * </p>
 */
public interface IRoomPriceService extends IService<RoomPrice> {

    int updateRoomNumber(int rid, int number, Date beginTime, Date endTime);

}
