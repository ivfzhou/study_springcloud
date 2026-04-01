package cn.ivfzhou.springcloud.orderserver.service;

import cn.ivfzhou.springcloud.entity.db.Order;
import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;
import cn.ivfzhou.springcloud.entity.db.OrderPriceResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单业务服务接口。
 * <p>
 * 继承 MyBatis-Plus 的 IService，提供订单实体（Order）的基础业务操作，
 * 并扩展了订单价格计算、下单、订单状态更新等业务方法。
 * </p>
 */

    OrderPriceResult getOrderPrice(OrderPriceParam params);

    String insertOrder(Order order, OrderPriceParam params);

    int updateOrderStatus(String oid, Integer status);

}
