package cn.ivfzhou.springcloud.feign;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;
import cn.ivfzhou.springcloud.entity.db.OrderPriceResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 订单服务 Feign 远程调用接口。
 * <p>
 * 用于其他微服务通过 OpenFeign 调用订单服务（micro-order）的内部接口，
 * 主要提供订单价格计算功能。
 * </p>
 */
@FeignClient("micro-order")
public interface OrderFeign {

    /** 根据价格参数计算订单价格（含每日明细和总价） */
    @RequestMapping("/order/getPriceFeign")
    ResultData<OrderPriceResult> getOrderPriceFeign(@RequestBody OrderPriceParam priceParams);

}
