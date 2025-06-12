package cn.ivfzhou.springcloud.hotelserver.dao;

import cn.ivfzhou.springcloud.entity.db.RoomPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface RoomPriceMapper extends BaseMapper<RoomPrice> {

    int updateRoomNumber(
            @Param("rid") int rid,
            @Param("number") int number,
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime);
}
