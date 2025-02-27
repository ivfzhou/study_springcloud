package cn.ivfzhou.reserve_platform.systemserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import cn.ivfzhou.reserve_platform.entity.db.Coupon;
import cn.ivfzhou.reserve_platform.entity.db.CouponIssue;
import cn.ivfzhou.reserve_platform.feign.CouponFeign;

@Controller
@Slf4j
@RequestMapping("/system")
public class SystemCouponController {

    @Autowired
    private CouponFeign dicFeign;

    /**
     * 优惠券列表。
     */
    @RequestMapping("couponlist")
    public String couponList(Model model) {
        List<Coupon> coupons = dicFeign.couponList().getData();
        model.addAttribute("coupons", coupons);
        return "couponlist";
    }

    /**
     * 跳转到新增优惠券页面。
     */
    @RequestMapping("/toCouponAdd")
    public String toCouponAdd() {
        return "couponadd";
    }

    /**
     * 新增优惠券。
     */
    @RequestMapping("/couponinsert")
    public String couponAdd(Coupon coupon) {
        //新增优惠券
        dicFeign.insert(coupon);
        return "redirect:/system/couponlist";
    }

    /**
     * 跳转到发布页面。
     */
    @RequestMapping("/toIssue")
    public String toIssue() {
        return "couponissue";
    }

    /**
     * 优惠券的发布。
     */
    @RequestMapping("/couponissue")
    public String couponIssue(CouponIssue couponIssue) {
        log.info("发布优惠券：" + couponIssue);

        // 调用服务保存到数据库
        dicFeign.issue(couponIssue);

        return "redirect:/system/couponlist";
    }

}
