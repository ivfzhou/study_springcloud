package cn.ivfzhou.springcloud.couponserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cn.ivfzhou.springcloud.common.util.DateUtil;
import cn.ivfzhou.springcloud.couponserver.dao.CouponIssueMapper;
import cn.ivfzhou.springcloud.couponserver.service.ICouponIssueService;
import cn.ivfzhou.springcloud.entity.db.CouponIssue;

@Service
@CacheConfig(cacheNames = "coupon")
public class CouponIssueServiceImpl extends ServiceImpl<CouponIssueMapper, CouponIssue> implements ICouponIssueService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @CacheEvict(key = "'time' + #entity.goStartTime.getTime()")
    public boolean save(CouponIssue entity) {
        boolean flag = super.save(entity);

        //发行优惠券
        if (entity.getMethod() == 1) {
            //定时抢购的逻辑
            //获得开始抢购的时间
            String beginTime = DateUtil.date2Str(entity.getGoStartTime(), "yyMMddHH");
            //将该优惠券的id保存到redis中
            redisTemplate.opsForSet().add("coupon_" + beginTime, entity.getCouponId() + "");

            //将优惠券的库存同步到redis中
            redisTemplate.opsForValue().set("coupon_save_" + entity.getCouponId(), entity.getNumber() + "");
        }

        return flag;
    }

}
