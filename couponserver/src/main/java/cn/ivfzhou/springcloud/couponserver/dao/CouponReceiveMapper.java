package cn.ivfzhou.springcloud.couponserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.ivfzhou.springcloud.entity.db.CouponReceive;

/**
 * 优惠券领取记录数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供优惠券领取记录表（t_coupon_receive）的基础 CRUD 操作。
 * </p>
 */
public interface CouponReceiveMapper extends BaseMapper<CouponReceive> {
}
