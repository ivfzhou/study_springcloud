package cn.ivfzhou.reserve_platform.couponserver.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import cn.ivfzhou.reserve_platform.couponserver.service.ICouponService;
import cn.ivfzhou.reserve_platform.event.EventListener;
import cn.ivfzhou.reserve_platform.event.constant.EventConstant;

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
        Integer uid = (Integer) msg.get("uid");
        Integer cid = (Integer) msg.get("cid");
        Long time = (Long) msg.get("now");
        log.info("用户id：" + uid + " 优惠券id：" + cid + " 时间：" + time);
        // 修改优惠券的库存。
        // 生成抢劵的记录。
        couponService.couponBuy(uid, cid, time);
    }

}
