package cn.ivfzhou.reserve_platform.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_history_price")
public class HistoryPrice implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer rid;

    private Date date;

    private BigDecimal price;

    private Integer number;

}
