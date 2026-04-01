package cn.ivfzhou.springcloud.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券领取记录实体类。
 * <p>
 * 对应数据库表 t_coupon_receive，记录用户领取优惠券的详细信息，
 * 包括领取时间、领取方式、过期时间和使用状态等。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_coupon_receive")
public class CouponReceive implements Serializable {

    /** 领取记录ID，主键自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 领取用户的ID */
    private Integer userId;

    /** 关联的优惠券ID */
    private Integer couponId;

    /** 领取时间 */
    private Date getTime;

    /** 领取方式，参考 {@link CouponIssueMethod} */
    private Integer getType;

    /** 优惠券过期时间 */
    private Date timeout;

    /** 使用状态，0-未使用，1-已使用 */
    private Integer status = 0;

}
