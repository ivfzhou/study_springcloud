package cn.ivfzhou.springcloud.couponserver.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ivfzhou.springcloud.common.util.DateUtil;
import cn.ivfzhou.springcloud.common.util.UserUtil;
import cn.ivfzhou.springcloud.couponserver.dao.CouponMapper;
import cn.ivfzhou.springcloud.couponserver.dao.CouponReceiveMapper;
import cn.ivfzhou.springcloud.couponserver.service.ICouponService;
import cn.ivfzhou.springcloud.entity.db.Coupon;
import cn.ivfzhou.springcloud.entity.db.CouponIssue;
import cn.ivfzhou.springcloud.entity.db.CouponIssueMethod;
import cn.ivfzhou.springcloud.entity.db.CouponReceive;
import cn.ivfzhou.springcloud.entity.db.OrderPriceParam;
import cn.ivfzhou.springcloud.entity.db.OrderPriceResult;
import cn.ivfzhou.springcloud.entity.db.User;
import cn.ivfzhou.springcloud.feign.OrderFeign;

@Service
@CacheConfig(cacheNames = "coupon")
@Slf4j
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponIssueServiceImpl issueService;

    @Autowired
    private CouponReceiveMapper couponReceiveMapper;

    @Autowired
    private OrderFeign orderFeign;

    @Override
    public List<Coupon> listCoupons() {
        User user = UserUtil.getUser();
        return couponMapper.getCouponByIssueType(CouponIssueMethod.CENTER.value, user.getId());
    }

    @Override
    public int receiveCoupon(Integer couponIssueId) {
        // 优惠券发行。
        CouponIssue issue = issueService.getById(couponIssueId);
        // 获得当前登录用户。
        User user = UserUtil.getUser();
        // 判断是否领取过。
        Long count = couponReceiveMapper.selectCount(new QueryWrapper<CouponReceive>().eq("cid", issue.getCouponId()).eq("uid", user.getId()));
        if (count == 0) {
            // 优惠券领取对象。
            CouponReceive couponReceive = new CouponReceive()
                    .setCouponId(issue.getCouponId())
                    .setUserId(user.getId())
                    .setGetTime(new Date())
                    .setGetType(issue.getMethod())
                    .setTimeout(issue.getType() == 0 ? issue.getEndTime() : DateUtil.getNextDate(issue.getDays()));
            return couponReceiveMapper.insert(couponReceive);
        }
        return 0;
    }

    @Override
    @Cacheable(key = "'time' + #time")
    public List<Coupon> listCouponByTime(long time) {
        String beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
        return couponMapper.getCouponByTime(1, beginTime);
    }

    @Override
    public Map<String, List<Coupon>> getCouponByUser(OrderPriceParam orderPriceParam) {
        User user = UserUtil.getUser();
        // 查询当前用户所有的优惠券。
        List<Coupon> coupons = couponMapper.getCouponByUser(user.getId());
        Map<String, List<Coupon>> resultMap = new HashMap<>();
        List<Coupon> canUser = new ArrayList<>();
        List<Coupon> unUser = new ArrayList<>();
        resultMap.put("canUser", canUser);
        resultMap.put("unUser", unUser);
        // 判断优惠券是否可用。
        for (Coupon coupon : coupons) {
            // 判断优惠券可用。
            boolean flag = coupon.getLimit().hasLimit(orderPriceParam);
            if (flag) {
                // 金额的验证。
                // 获得本地订单的下单额度。
                OrderPriceResult result = orderFeign.getOrderPriceFeign(orderPriceParam).getData();
                // 总金额。
                double allprice = result.getAllPrice();
                // 判断额度。
                if (coupon.getRule().hasPrice(allprice)) {
                    //当前优惠券本次下单可用
                    canUser.add(coupon);
                    continue;
                }
            }

            // 优惠券不可用.
            unUser.add(coupon);
        }

        return resultMap;
    }

    @Override
    @Transactional
    public int couponBuy(Integer uid, Integer cid, Long time) {
        // 查询发行的记录。
        CouponIssue issue = issueService.getOne(new QueryWrapper<CouponIssue>().eq("coupon_id", cid).eq("method", 1));
        issue.setNumber(issue.getNumber() - 1);
        // 修改库存。
        issueService.updateById(issue);
        // 生成领取记录。
        CouponReceive couponReceive = new CouponReceive()
                .setCouponId(cid)
                .setUserId(uid)
                .setGetTime(new Date(time))
                .setGetType(issue.getMethod())
                .setTimeout(issue.getType() == 0 ? issue.getEndTime() : DateUtil.getNextDate(issue.getDays()));
        couponReceiveMapper.insert(couponReceive);
        return 1;
    }

}
