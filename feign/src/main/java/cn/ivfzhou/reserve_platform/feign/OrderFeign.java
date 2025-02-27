package cn.ivfzhou.reserve_platform.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ivfzhou.reserve_platform.entity.db.OrderPriceParam;
import cn.ivfzhou.reserve_platform.entity.db.OrderPriceResult;
import cn.ivfzhou.reserve_platform.entity.ResultData;

@FeignClient("micro-order")
public interface OrderFeign {

    @RequestMapping("/order/getPriceFeign")
    ResultData<OrderPriceResult> getOrderPriceFeign(@RequestBody OrderPriceParam priceParams);

}
