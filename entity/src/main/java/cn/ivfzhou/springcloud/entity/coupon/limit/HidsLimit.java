package cn.ivfzhou.springcloud.entity.coupon.limit;

import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;
import lombok.Data;

/**
 * 酒店ID的限制
 */
@Data
public class HidsLimit implements ILimit {

    //可用的酒店id集合
    private String hids;

    @Override
    public boolean hasLimit(OrderPriceParam orderPriceParam) {

        //下单的酒店id
        Integer hid = orderPriceParam.getHid();

        //获得允许优惠的酒店id
        String[] hidsArray = hids.split(",");
        for (String s : hidsArray) {
            if (Integer.parseInt(s) == hid) {
                // 可以参与优惠
                return true;
            }
        }

        return false;
    }
}
