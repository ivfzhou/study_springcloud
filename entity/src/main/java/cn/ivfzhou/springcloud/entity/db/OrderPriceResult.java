package cn.ivfzhou.springcloud.entity.db;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 订单价格计算结果实体类。
 * <p>
 * 非数据库实体，用于封装订单价格计算后的返回结果。
 * 包含订单总价格和每日价格明细列表。
 * </p>
 */
@Data
@Accessors(chain = true)
public class OrderPriceResult implements Serializable {

    /** 订单总价格（已扣除优惠券优惠） */
    private double allPrice;

    /** 每日价格明细，二维列表，每项包含 [日期, 价格] */
    private List<List<String>> priceDetails;

}
