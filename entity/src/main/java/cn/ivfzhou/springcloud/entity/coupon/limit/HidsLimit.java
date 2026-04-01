package cn.ivfzhou.springcloud.entity.coupon.limit;

import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;
import lombok.Data;

/**
 * 酒店ID限制实现类。
 * <p>
 * 限制优惠券只能在指定的酒店集合中使用。
 * 通过逗号分隔的酒店ID字符串配置允许使用优惠券的酒店列表。
 * </p>
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
