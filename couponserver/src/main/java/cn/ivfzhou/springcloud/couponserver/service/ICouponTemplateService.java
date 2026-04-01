package cn.ivfzhou.springcloud.couponserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.ivfzhou.springcloud.entity.db.CouponTemplate;

/**
 * 优惠券模板服务接口。
 * <p>
 * 继承 MyBatis-Plus 的 IService，提供优惠券模板表（t_coupon_template）的基础业务操作。
 * </p>
 */
public interface ICouponTemplateService extends IService<CouponTemplate> {
}
