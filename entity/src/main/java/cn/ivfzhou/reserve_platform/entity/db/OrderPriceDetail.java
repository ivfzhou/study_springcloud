package cn.ivfzhou.reserve_platform.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_order_price_detail")
public class OrderPriceDetail implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String oid;

    private Date time;

    private double price;

    private Date createTime = new Date();

    private Integer status = 0;

}
