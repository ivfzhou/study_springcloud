package cn.ivfzhou.reserve_platform.entity.coupon.limit;

import cn.ivfzhou.reserve_platform.entity.db.OrderPriceParam;

import java.io.Serializable;

/**
 * 限制的规范
 */
public interface ILimit extends Serializable {

    /**
     * 判断下单的酒店是否符合优惠的限制
     */
    boolean hasLimit(OrderPriceParam orderPriceParam);

}
