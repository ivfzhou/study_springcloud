package cn.ivfzhou.springcloud.hotelserver.service;

import cn.ivfzhou.springcloud.entity.db.Hotel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 酒店业务服务接口。
 * <p>
 * 继承 MyBatis-Plus 的 IService，提供酒店实体（Hotel）的基础业务操作。
 * </p>
 */
public interface IHotelService extends IService<Hotel> {
}
