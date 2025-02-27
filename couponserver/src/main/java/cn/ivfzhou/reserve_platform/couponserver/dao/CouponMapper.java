package cn.ivfzhou.reserve_platform.couponserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.ivfzhou.reserve_platform.entity.db.Coupon;

public interface CouponMapper extends BaseMapper<Coupon> {

    /**
     * 根据发布的形式查询优惠券列表。
     */
    List<Coupon> getCouponByIssueType(@Param("method") int method, @Param("uid") int uid);

    /**
     * 根据发布的形式查询优惠券列表。
     */
    List<Coupon> getCouponByTime(@Param("method") int method, @Param("time") String time);

    /**
     * 根据用户id查询优惠券列表。
     */
    List<Coupon> getCouponByUser(@Param("uid") int uid);

}
