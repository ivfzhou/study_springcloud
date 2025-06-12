package cn.ivfzhou.springcloud.orderserver.service;

import cn.ivfzhou.springcloud.entity.db.Order;
import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;
import cn.ivfzhou.springcloud.entity.db.OrderPriceResult;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IOrderService extends IService<Order> {

    OrderPriceResult getOrderPrice(OrderPriceParam params);

    String insertOrder(Order order, OrderPriceParam params);

    int updateOrderStatus(String oid, Integer status);

}
