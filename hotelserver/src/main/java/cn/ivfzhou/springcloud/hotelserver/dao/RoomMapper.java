package cn.ivfzhou.springcloud.hotelserver.dao;

import cn.ivfzhou.springcloud.entity.db.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 客房数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供客房表（t_room）的基础 CRUD 操作。
 * </p>
 */
public interface RoomMapper extends BaseMapper<Room> {
}
