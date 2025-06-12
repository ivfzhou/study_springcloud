package cn.ivfzhou.springcloud.couponserver.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ivfzhou.springcloud.common.annotation.NeedLogin;
import cn.ivfzhou.springcloud.couponserver.service.ICouponService;
import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.Coupon;
import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;

@RestController
@RequestMapping("/web/coupon")
public class CouponWebController {

    @Autowired
    private ICouponService couponService;

    /**
     * 获取用户优惠券中心列表。
     */
    @GetMapping("/list")
    @NeedLogin
    public ResultData<List<Coupon>> list() {
        return new ResultData<List<Coupon>>().setData(couponService.listCoupons());
    }

    /**
     * 领取优惠券。
     */
    @PostMapping("/receive")
    @NeedLogin
    public ResultData<Boolean> receive(Integer couponIssueId) {
        return new ResultData<Boolean>().setData(couponService.receiveCoupon(couponIssueId) > 0);
    }

    /**
     * 订单编辑，查询用户当前可用的优惠券列表。
     */
    @PostMapping("/couponUser")
    public ResultData<Map<String, List<Coupon>>> couponUser(@RequestBody OrderPriceParam orderPriceParam) {
        return new ResultData<Map<String, List<Coupon>>>().setData(
                couponService.getCouponByUser(orderPriceParam));
    }

    /**
     * 根据 ID 查询优惠券信息。
     */
    @RequestMapping("/couponById")
    public ResultData<Coupon> getCouponById(@RequestParam Integer couponId) {
        return new ResultData<Coupon>().setData(couponService.getById(couponId));
    }

    /**
     * 根据抢劵的开始时间，查询该时间段的优惠券列表。
     */
    @RequestMapping("/couponListByTime")
    public ResultData<List<Coupon>> couponListByTime(long time) {
        return new ResultData<List<Coupon>>().setData(couponService.listCouponByTime(time));
    }

}
