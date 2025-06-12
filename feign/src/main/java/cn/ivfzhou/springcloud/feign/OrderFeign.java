package cn.ivfzhou.springcloud.feign;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;
import cn.ivfzhou.springcloud.entity.db.OrderPriceResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("micro-order")
public interface OrderFeign {

    @RequestMapping("/order/getPriceFeign")
    ResultData<OrderPriceResult> getOrderPriceFeign(@RequestBody OrderPriceParam priceParams);

}
