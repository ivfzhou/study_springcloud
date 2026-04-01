package cn.ivfzhou.springcloud.entity.db;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 优惠券发放实体类。
 * <p>
 * 对应数据库表 t_coupon_issue，记录优惠券的发放方式、有效期限、发放数量等信息。
 * 支持定时抢购模式，可设置抢购开始和结束时间。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_coupon_issue")
public class CouponIssue implements Serializable {

    /** 发放记录ID，主键自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 关联的优惠券ID */
    private Integer couponId;

    /** 发放方式，0-领券中心，1-定时抢购 */
    private Integer method;

    /** 优惠券有效期的类型 */
    private Integer type;

    /** 有效期开始日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    /** 有效期结束日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 优惠券有效天数（与固定日期互斥） */
    private Integer days;

    /** 发放总数 */
    private Integer number;

    // 定时抢购开始的时间。
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date goStartTime;

    // 定时抢购结束的时间。
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date goStopTime;

}
