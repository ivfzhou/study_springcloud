package cn.ivfzhou.reserve_platform.entity.coupon.rule;

import java.io.Serializable;

/**
 * 规则的接口 - 规范
 */
public interface IRule extends Serializable {

    /**
     * 下单的金额是否满足优惠的条件
     */
    boolean hasPrice(double price);

    /**
     * 进行价格的优惠， 返回优惠后的结果
     */
    double discount(double price);

    /**
     * 优惠了多少金额，返回扣减的金额数
     */
    double discountPrice(double price);

}
