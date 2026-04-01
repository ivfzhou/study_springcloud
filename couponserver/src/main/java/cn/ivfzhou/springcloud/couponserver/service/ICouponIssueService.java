package cn.ivfzhou.springcloud.couponserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.ivfzhou.springcloud.entity.db.CouponIssue;

/**
 * 优惠券发布服务接口。
 * <p>
 * 继承 MyBatis-Plus 的 IService，提供优惠券发布表（t_coupon_issue）的基础业务操作。
 * </p>
 */
public interface ICouponIssueService extends IService<CouponIssue> {
}
