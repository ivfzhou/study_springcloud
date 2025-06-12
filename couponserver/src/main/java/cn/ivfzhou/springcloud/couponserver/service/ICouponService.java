package cn.ivfzhou.springcloud.couponserver.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.ivfzhou.springcloud.entity.db.Coupon;
import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;

public interface ICouponService extends IService<Coupon> {

    /**
     * 优惠券中心列表。
     */
    List<Coupon> listCoupons();

    /**
     * 领取优惠券。
     */
    int receiveCoupon(Integer couponIssueId);

    /**
     * 根据时间戳查询优惠券列表。
     */
    List<Coupon> listCouponByTime(long time);

    /**
     * 查询用户在订单编辑时，可用的优惠券列表。
     */
    Map<String, List<Coupon>> getCouponByUser(OrderPriceParam orderPriceParam);

    /**
     * 生成优惠券抢劵的记录。
     */
    int couponBuy(Integer uid, Integer cid, Long time);

}
