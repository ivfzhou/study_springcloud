package cn.ivfzhou.springcloud.entity.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单价格查询参数实体类。
 * <p>
 * 对应数据库表 t_order_price_param，作为价格计算的查询条件载体。
 * 包含酒店、房间、入住日期、房间数量和使用的优惠券等信息。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_order_price_param")
public class OrderPriceParam implements Serializable {

    /** 酒店ID */
    private Integer hid;

    /** 客房类型ID */
    private Integer rid;

    /** 入住日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    /** 离店日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 预订房间数量 */
    private Integer rnumber;

    /** 使用的优惠券ID */
    private Integer cid;

}
