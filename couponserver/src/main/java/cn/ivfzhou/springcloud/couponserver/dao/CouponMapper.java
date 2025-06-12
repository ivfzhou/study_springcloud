package cn.ivfzhou.springcloud.couponserver.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import cn.ivfzhou.springcloud.entity.db.Coupon;

public interface CouponMapper extends BaseMapper<Coupon> {

    /**
     * 根据发布的形式查询优惠券列表。
     */
    List<Coupon> getCouponByIssueType(@Param("method") int method, @Param("userId") int userId);

    /**
     * 根据发布的形式查询优惠券列表。
     */
    List<Coupon> getCouponByTime(@Param("method") int method, @Param("time") String time);

    /**
     * 根据用户 ID 查询优惠券列表。
     */
    List<Coupon> getCouponByUser(@Param("userId") int userId);

}
