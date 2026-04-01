package cn.ivfzhou.springcloud.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单价格明细实体类。
 * <p>
 * 对应数据库表 t_order_price_detail，记录订单中每天的房间价格信息。
 * 用于订单详情展示和价格追溯。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_order_price_detail")
public class OrderPriceDetail implements Serializable {

    /** 明细记录ID，主键自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 关联的订单号 */
    private String oid;

    /** 价格对应的日期 */
    private Date time;

    /** 当日房间价格 */
    private double price;

    /** 记录创建时间，默认为当前时间 */
    private Date createTime = new Date();

    /** 状态标记，0-正常 */
    private Integer status = 0;

}
