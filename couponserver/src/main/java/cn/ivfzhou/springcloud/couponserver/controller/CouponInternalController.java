package cn.ivfzhou.springcloud.couponserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ivfzhou.springcloud.couponserver.service.ICouponIssueService;
import cn.ivfzhou.springcloud.couponserver.service.ICouponService;
import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.Coupon;
import cn.ivfzhou.springcloud.entity.db.CouponIssue;

@RestController
@RequestMapping("/internal/coupon")
public class CouponInternalController {

    @Autowired
    private ICouponService couponService;

    @Autowired
    private ICouponIssueService issueService;

    /**
     * 获取所有优惠券。
     */
    @GetMapping("/list")
    public ResultData<List<Coupon>> list() {
        return new ResultData<List<Coupon>>().setData(couponService.list());
    }

    /**
     * 新增优惠券。
     */
    @PutMapping("/add")
    public ResultData<Boolean> add(@RequestBody Coupon coupon) {
        return new ResultData<Boolean>().setData(couponService.save(coupon));
    }

    /**
     * 发布优惠券。
     */
    @PutMapping("/issue")
    public ResultData<Boolean> issue(@RequestBody CouponIssue couponIssue) {
        return new ResultData<Boolean>().setData(issueService.save(couponIssue));
    }
}
