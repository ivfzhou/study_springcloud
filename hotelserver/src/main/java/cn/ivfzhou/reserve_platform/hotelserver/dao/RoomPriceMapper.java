package cn.ivfzhou.reserve_platform.hotelserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

import cn.ivfzhou.reserve_platform.entity.db.RoomPrice;

public interface RoomPriceMapper extends BaseMapper<RoomPrice> {

    int updateRoomNumber(
            @Param("rid") int rid,
            @Param("number") int number,
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime);
}
