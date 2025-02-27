package cn.ivfzhou.reserve_platform.entity.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_order_price_param")
public class OrderPriceParam implements Serializable {

    private Integer hid;

    private Integer rid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Integer rnumber;

    // 优惠券id。
    private Integer cid;

}
