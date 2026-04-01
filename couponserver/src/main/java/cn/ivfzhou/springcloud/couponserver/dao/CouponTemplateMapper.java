package cn.ivfzhou.springcloud.couponserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.ivfzhou.springcloud.entity.db.CouponTemplate;

/**
 * 优惠券模板数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供优惠券模板表（t_coupon_template）的基础 CRUD 操作。
 * </p>
 */
public interface CouponTemplateMapper extends BaseMapper<CouponTemplate> {
}
