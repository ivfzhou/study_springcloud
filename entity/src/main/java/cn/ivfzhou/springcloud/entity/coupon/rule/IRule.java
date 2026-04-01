package cn.ivfzhou.springcloud.entity.coupon.rule;

import java.io.Serializable;

/**
 * 优惠规则接口（策略模式）。
 * <p>
 * 定义优惠券优惠计算的规范，所有优惠规则实现类都需要实现此接口。
 * 判断订单金额是否满足优惠条件，并计算优惠后的价格和优惠金额。
 * </p>
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
