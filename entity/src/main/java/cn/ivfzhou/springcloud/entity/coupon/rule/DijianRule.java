package cn.ivfzhou.springcloud.entity.coupon.rule;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 每满递减优惠规则实现类。
 * <p>
 * 每满指定金额递减固定金额，设有最大减免上限。
 * 例如：每满200减10，最多减50，则 meiman=200, dijian=10, maxjian=50。
 * </p>
 */

    private double meiman; // 每满

    private double dijian; // 递减

    private double maxjian; // 满减


    @Override
    public boolean hasPrice(double price) {
        return price >= meiman;
    }

    @Override
    public double discount(double price) {
        //计算价格满了几次 meiman
        int has = (int) (price / meiman);
        //用次数乘以递减的价格
        double youhui = BigDecimal.valueOf(dijian).multiply(BigDecimal.valueOf(has)).doubleValue();
        return price - Math.min(youhui, maxjian);
    }

    @Override
    public double discountPrice(double price) {
        return price - discount(price);
    }

}
