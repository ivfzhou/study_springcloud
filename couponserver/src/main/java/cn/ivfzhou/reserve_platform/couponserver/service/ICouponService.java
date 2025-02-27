package cn.ivfzhou.reserve_platform.couponserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import cn.ivfzhou.reserve_platform.entity.db.Coupon;
import cn.ivfzhou.reserve_platform.entity.db.OrderPriceParam;

public interface ICouponService extends IService<Coupon> {

    List<Coupon> couponCore();

    int recCoupon(Integer issid);

    List<Coupon> couponListByTime(long time);

    Map<String, List<Coupon>> getCouponByUser(OrderPriceParam orderPriceParam);

    int couponBuy(Integer uid, Integer cid, Long time);

}
