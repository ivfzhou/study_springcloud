package cn.ivfzhou.springcloud.entity.coupon.rule;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DijianRule implements IRule {

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
