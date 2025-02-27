package cn.ivfzhou.reserve_platform.orderserver.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ivfzhou.reserve_platform.entity.db.OrderPriceParam;
import cn.ivfzhou.reserve_platform.entity.db.OrderPriceResult;
import cn.ivfzhou.reserve_platform.entity.db.Order;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.common.login.IsLogin;
import cn.ivfzhou.reserve_platform.orderserver.service.IOrderService;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 获得订单价格明细
     */
    @RequestMapping("/getPrice")
    public ResultData<OrderPriceResult> getOrderPrice(OrderPriceParam priceParams) {
        System.out.println("订单明细的参数：" + priceParams);
        OrderPriceResult orderPrice = orderService.getOrderPrice(priceParams);
        return new ResultData<OrderPriceResult>().setData(orderPrice);
    }

    /**
     * 获得订单价格明细
     */
    @RequestMapping("/getPriceFeign")
    public ResultData<OrderPriceResult> getOrderPriceFeign(@RequestBody OrderPriceParam priceParams) {
        OrderPriceResult orderPrice = orderService.getOrderPrice(priceParams);
        return new ResultData<OrderPriceResult>().setData(orderPrice);
    }

    /**
     * 下单
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/insert")
    public ResultData<String> insertOrder(Order order, OrderPriceParam orderPriceParam) {
        System.out.println("下单对象：" + order);
        System.out.println("价格参数对象：" + orderPriceParam);
        String orderid = orderService.insertOrder(order, orderPriceParam);
        if (orderid != null)
            return new ResultData<String>().setData(orderid);
        else
            return new ResultData<String>().setCode(ResultData.Code.ERROR).setMsg("房间不够，下单失败！");
    }

}
