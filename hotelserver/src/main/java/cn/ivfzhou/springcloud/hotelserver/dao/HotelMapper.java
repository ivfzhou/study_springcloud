package cn.ivfzhou.springcloud.hotelserver.dao;

import cn.ivfzhou.springcloud.entity.db.Hotel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 酒店数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供酒店表（t_hotel）的基础 CRUD 操作。
 * </p>
 */
public interface HotelMapper extends BaseMapper<Hotel> {
}
