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
@TableName("t_coupon_receive")
public class CouponReceive implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private Integer cid;

    private Date getTime;

    private Integer getType;

    private Date timeout;

    private Integer status = 0;

}
