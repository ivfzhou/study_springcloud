package cn.ivfzhou.reserve_platform.couponserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import cn.ivfzhou.reserve_platform.couponserver.dao.CouponMapper;
import cn.ivfzhou.reserve_platform.couponserver.dao.CouponRecMapper;
import cn.ivfzhou.reserve_platform.couponserver.service.ICouponService;
import cn.ivfzhou.reserve_platform.feign.OrderFeign;
import cn.ivfzhou.reserve_platform.common.login.UserUtil;
import cn.ivfzhou.reserve_platform.entity.db.*;
import cn.ivfzhou.reserve_platform.common.util.DateUtil;

@Service
@CacheConfig(cacheNames = "coupon")
@Slf4j
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private IssueServiceImpl issueService;

    @Autowired
    private CouponRecMapper couponRecMapper;

    @Autowired
    private OrderFeign orderFeign;

    /**
     * 查询优惠券中心
     */
    @Override
    public List<Coupon> couponCore() {
        User user = UserUtil.getUser();
        log.info("--->" + user);
        // 查询领劵中心的所有优惠券。
        return couponMapper.getCouponByIssueType(0, user != null ? user.getId() : -1);
    }

    /**
     * 领取优惠券
     */
    @Override
    public int recCoupon(Integer issid) {
        // 优惠券发行。
        CouponIssue issue = issueService.getById(issid);
        // 获得当前登录用户。
        User user = UserUtil.getUser();
        // 判断是否领取过。
        Long count = couponRecMapper.selectCount(new QueryWrapper<CouponReceive>().eq("cid", issue.getCid()).eq("uid", user.getId()));
        if (count == 0) {
            // 优惠券领取对象。
            CouponReceive couponReceive = new CouponReceive()
                    .setCid(issue.getCid())
                    .setUid(user.getId())
                    .setGetTime(new Date())
                    .setGetType(issue.getMethod())
                    .setTimeout(issue.getType() == 0 ? issue.getEndTime() : DateUtil.getNextDate(issue.getDays()));
            return couponRecMapper.insert(couponReceive);
        }
        return 0;
    }

    /**
     * 根据时间戳查询优惠券列表。
     */
    @Override
    @Cacheable(key = "'time' + #time")
    public List<Coupon> couponListByTime(long time) {
        String beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
        log.info("查询当前抢劵场次时间：" + beginTime);
        return couponMapper.getCouponByTime(1, beginTime);
    }

    /**
     * 查询用户在订单编辑时，可用的优惠券列表。
     */
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

    /**
     * 生成优惠券抢劵的记录。
     */
    @Override
    @Transactional
    public int couponBuy(Integer uid, Integer cid, Long time) {
        // cid 查询发行的记录。
        CouponIssue issue = issueService.getOne(new QueryWrapper<CouponIssue>().eq("cid", cid).eq("method", 1));
        issue.setNumber(issue.getNumber() - 1);
        // 修改库存。
        issueService.updateById(issue);
        // 生成领取记录。
        CouponReceive couponReceive = new CouponReceive()
                .setCid(cid)
                .setUid(uid)
                .setGetTime(new Date(time))
                .setGetType(issue.getMethod())
                .setTimeout(issue.getType() == 0 ? issue.getEndTime() : DateUtil.getNextDate(issue.getDays()));
        couponRecMapper.insert(couponReceive);
        return 1;
    }

}
