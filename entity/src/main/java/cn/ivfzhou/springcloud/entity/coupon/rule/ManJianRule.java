package cn.ivfzhou.springcloud.entity.coupon.rule;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 满减优惠规则实现类。
 * <p>
 * 当订单金额满足最低消费门槛时，直接减免固定金额。
 * 例如：满100减20，则 mustPrice=100, jianPrice=20。
 * </p>
 */
@Data
public class ManJianRule implements IRule {

    /** 最低消费门槛金额 */
    private double mustPrice;

    /** 满减金额 */
    private double jianPrice;

    /**
     * 下单的金额是否满足优惠的条件
     */
    @Override
    public boolean hasPrice(double price) {
        return mustPrice <= price;
    }

    /**
     * 优惠的形式
     */
    @Override
    public double discount(double price) {
        return BigDecimal.valueOf(price).subtract(BigDecimal.valueOf(jianPrice)).doubleValue();
    }

    /**
     * 优惠了多少额度
     */
    @Override
    public double discountPrice(double price) {
        return jianPrice;
    }

}
