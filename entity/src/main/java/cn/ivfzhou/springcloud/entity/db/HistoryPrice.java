package cn.ivfzhou.springcloud.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客房历史价格实体类。
 * <p>
 * 对应数据库表 t_history_price，记录酒店客房的每日历史价格和可用房间数量。
 * 用于价格趋势展示和历史订单价格回溯。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_history_price")
public class HistoryPrice implements Serializable {

    /** 历史价格记录ID，主键自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 关联的客房类型ID */
    private Integer rid;

    /** 价格对应的日期 */
    private Date date;

    /** 当日价格 */
    private BigDecimal price;

    /** 当日可用房间数量 */
    private Integer number;

}
