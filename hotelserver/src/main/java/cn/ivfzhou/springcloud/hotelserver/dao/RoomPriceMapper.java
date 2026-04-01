package cn.ivfzhou.springcloud.hotelserver.dao;

import cn.ivfzhou.springcloud.entity.db.RoomPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 客房价格数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供客房价格表（t_room_price）的基础 CRUD 操作，
 * 并自定义了按日期范围更新剩余数量的方法。
 * </p>
 */
public interface RoomPriceMapper extends BaseMapper<RoomPrice> {

    int updateRoomNumber(
            @Param("rid") int rid,
            @Param("number") int number,
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime);
}
