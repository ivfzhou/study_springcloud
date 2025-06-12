package cn.ivfzhou.springcloud.entity.coupon.rule;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 满减的规则实现类
 */
@Data
public class ManJianRule implements IRule {

    private double mustPrice;

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
