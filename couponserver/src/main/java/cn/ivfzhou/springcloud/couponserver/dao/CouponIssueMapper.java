package cn.ivfzhou.springcloud.couponserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.ivfzhou.springcloud.entity.db.CouponIssue;

/**
 * 优惠券发布数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供优惠券发布表（t_coupon_issue）的基础 CRUD 操作。
 * </p>
 */
public interface CouponIssueMapper extends BaseMapper<CouponIssue> {
}
