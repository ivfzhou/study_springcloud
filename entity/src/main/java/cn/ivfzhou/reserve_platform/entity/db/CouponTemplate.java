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
@TableName("t_coupon_template")
public class CouponTemplate implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String tname;

    private Date createTime = new Date();

    private Integer status = 0;

    private String templateClass;

    private String templateDynamic;

    private Integer templateType;

}
