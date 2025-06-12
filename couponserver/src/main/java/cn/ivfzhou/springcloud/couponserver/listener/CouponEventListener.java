package cn.ivfzhou.springcloud.couponserver.listener;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.ivfzhou.springcloud.couponserver.service.ICouponService;
import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;

/**
 * 优惠券抢劵事件的监听处理。
 */
@Component
@Slf4j
public class CouponEventListener implements EventListener<Map<String, Object>> {

    @Autowired
    private ICouponService couponService;

    @Override
    public String getEventType() {
        return EventConstant.EVENT_COUPON_GET;
    }

    @Override
    public void eventHandler(Map<String, Object> msg) {
        Integer uid = (Integer) msg.get("userId");
        Integer cid = (Integer) msg.get("couponId");
        Long time = (Long) msg.get("now");
        log.info("用户 {} 优惠券 {} 时间 {}", uid, cid, time);
        // 修改优惠券的库存。
        // 生成抢劵的记录。
        couponService.couponBuy(uid, cid, time);
    }

}
