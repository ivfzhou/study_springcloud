package cn.ivfzhou.reserve_platform.couponserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ivfzhou.reserve_platform.couponserver.service.ICouponService;
import cn.ivfzhou.reserve_platform.couponserver.service.IIssueService;
import cn.ivfzhou.reserve_platform.entity.db.Coupon;
import cn.ivfzhou.reserve_platform.entity.db.CouponIssue;
import cn.ivfzhou.reserve_platform.entity.db.OrderPriceParam;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.common.login.IsLogin;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private ICouponService couponService;

    @Autowired
    private IIssueService issueService;

    /**
     * 优惠券列表
     */
    @RequestMapping("/list")
    public ResultData<List<Coupon>> list() {
        System.out.println("有请求访问了优惠券列表:" + new Date());
        List<Coupon> list = couponService.list();
        return new ResultData<List<Coupon>>().setData(list);
    }

    /**
     * 新增优惠券
     */
    @PutMapping("/insert")
    public ResultData<Boolean> insert(@RequestBody Coupon coupon) {
        boolean save = couponService.save(coupon);
        return new ResultData<Boolean>().setData(save);
    }

    /**
     * 发布优惠券
     */
    @PutMapping("/issue")
    public ResultData<Boolean> issue(@RequestBody CouponIssue couponIssue) {
        boolean flag = issueService.save(couponIssue);
        return new ResultData<Boolean>().setData(flag);
    }

    /**
     * 查询优惠券中心列表
     */
    @GetMapping("/couponCore")
    @IsLogin
    public ResultData<List<Coupon>> couponCore() {
        List<Coupon> coupons = couponService.couponCore();
        return new ResultData<List<Coupon>>().setData(coupons);
    }

    /**
     * 领取优惠券
     */
    @IsLogin(mustLogin = true)
    @PostMapping("/couponRec")
    public ResultData<Boolean> couponRec(Integer issid) {
        //领取指定的优惠券
        int result = couponService.recCoupon(issid);
        return new ResultData<Boolean>().setData(result > 0);
    }

    /**
     * 订单编辑 - 查询用户当前可用的优惠券列表
     */
    @IsLogin(mustLogin = true)
    @PostMapping("/couponUser")
    public ResultData<Map<String, List<Coupon>>> couponUser(@RequestBody OrderPriceParam orderPriceParam) {
        Map<String, List<Coupon>> coupons = couponService.getCouponByUser(orderPriceParam);
        return new ResultData<Map<String, List<Coupon>>>().setData(coupons);
    }

    /**
     * 根据id查询优惠券信息
     */
    @RequestMapping("/couponById")
    public ResultData<Coupon> getCouponById(@RequestParam Integer cid) {
        Coupon coupon = couponService.getById(cid);
        return new ResultData<Coupon>().setData(coupon);
    }

    /**
     * 根据抢劵的开始时间，查询该时间段的优惠券列表
     */
    @RequestMapping("/couponListByTime")
    public ResultData<List<Coupon>> couponListByTime(long time) {
        List<Coupon> coupons = couponService.couponListByTime(time);
        return new ResultData<List<Coupon>>().setData(coupons);
    }

}
