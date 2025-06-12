package cn.ivfzhou.springcloud.entity.db;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("t_coupon_template")
public class CouponTemplate implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Date createTime = new Date();

    private Integer status;

    @TableField("class")
    private String templateClass;

    private String dynamic;

    private Integer type;

}
