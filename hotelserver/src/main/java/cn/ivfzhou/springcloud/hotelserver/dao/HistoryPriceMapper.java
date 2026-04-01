package cn.ivfzhou.springcloud.hotelserver.dao;

import cn.ivfzhou.springcloud.entity.db.HistoryPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 历史价格数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供历史价格表（t_history_price）的基础 CRUD 操作。
 * </p>
 */
public interface HistoryPriceMapper extends BaseMapper<HistoryPrice> {
}
