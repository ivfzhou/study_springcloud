package cn.ivfzhou.springcloud.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 酒店预订订单实体类。
 * <p>
 * 对应数据库表 t_order，存储用户的酒店预订订单信息。
 * 包括订单号（雪花算法生成）、用户信息、酒店房间信息、入住时间和价格等。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_order")
public class Order implements Serializable {

    /** 订单号，雪花算法生成的主键 */
    @TableId(type = IdType.INPUT)
    private String oid;

    /** 下单用户ID */
    private Integer uid;

    /** 预订酒店ID */
    private Integer hid;

    /** 预订客房类型ID */
    private Integer rid;

    /** 预订房间数量 */
    private Integer number;

    /** 入住人姓名 */
    private String name;

    /** 入住人手机号 */
    private String phone;

    /** 入住日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    /** 离店日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 入住天数 */
    private Integer days;

    /** 订单总金额 */
    private double allPrice;

    /** 下单时间，默认为当前时间 */
    private Date createTime = new Date();

    /** 订单状态，0-待支付，1-已支付，2-已取消等 */
    private Integer status = 0;

}
