package cn.ivfzhou.reserve_platform.orderserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.ivfzhou.reserve_platform.entity.db.OrderPriceParam;
import cn.ivfzhou.reserve_platform.entity.db.OrderPriceResult;
import cn.ivfzhou.reserve_platform.entity.db.Order;

public interface IOrderService extends IService<Order> {

    OrderPriceResult getOrderPrice(OrderPriceParam params);

    String insertOrder(Order order, OrderPriceParam params);

    int updateOrderStatus(String oid, Integer status);

}
