package cn.ivfzhou.springcloud.entity.coupon.limit;

import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;

import java.io.Serializable;

/**
 * 优惠券使用限制接口（策略模式）。
 * <p>
 * 定义优惠券使用限制的规范，判断下单的订单参数是否符合优惠的使用限制条件。
 * </p>
 */
public interface ILimit extends Serializable {

    /**
     * 判断下单的酒店是否符合优惠的限制
     */
    boolean hasLimit(OrderPriceParam orderPriceParam);

}
