package cn.ivfzhou.springcloud.purchaseserver.controller;

import cn.ivfzhou.springcloud.common.annotation.NeedLogin;
import cn.ivfzhou.springcloud.common.util.UserUtil;
import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import cn.ivfzhou.springcloud.rabbitmq.util.EventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    // lua脚本。
    private final String luaScript = "-- 抢购的优惠券id\n" +
            "local cid=KEYS[1]\n" +
            "-- 抢购的数量\n" +
            "local gnumber=1\n" +
            "-- 获得优惠券剩余数量\n" +
            "local csave=tonumber(redis.call('get', 'coupon_save_'..cid) or 0)\n" +
            "-- 判断库存\n" +
            "if csave>=gnumber then\n" +
            "-- 库存足够，可以抢购\n" +
            "local result=redis.call('decrby', 'coupon_save_'..cid, gnumber)\n" +
            "-- 返回当前剩余的库存\n" +
            "return tonumber(result)\n" +
            "end\n" +
            "-- 库存不足\n" +
            "return -1";
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EventUtil eventUtil;

    /**
     * 查询当前可查看的抢劵的时间段
     * 整点抢劵，偶数时间 0 6 8
     */
    @RequestMapping("/times")
    public ResultData<List<Map<String, Object>>> queryPurchaseTimes() {
        List<Map<String, Object>> times = new ArrayList<>();
        // 获得当前最早显示的场次。
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        for (int i = 0; i < 3; i++) {
            // 每次循环都基于当前时间生成时间戳。
            calendar = Calendar.getInstance();
            // 判断是否为偶数。
            int first = hour % 2 == 0 ? hour : hour - 1;
            first = first + 2 * i;
            calendar.set(Calendar.HOUR_OF_DAY, first);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Map<String, Object> map = new HashMap<>();
            map.put("showTitle", calendar.get(Calendar.HOUR_OF_DAY) + "点场");
            map.put("queryInfo", calendar.getTime().getTime());
            times.add(map);
        }


        return new ResultData<List<Map<String, Object>>>().setData(times);
    }

    /**
     * 获得当前时间。
     */
    @RequestMapping("/now")
    public ResultData<Long> getNowTime() {
        return new ResultData<Long>().setData(new Date().getTime());
    }

    /**
     * 抢购优惠券的业务。
     */
    @RequestMapping("/get")
    @NeedLogin(mustLogin = true)
    public ResultData<Integer> getCoupon(Integer cid) {
        // 通过lua脚本进行库存一致性的判定。
        Long csave = redisTemplate.execute(
                new DefaultRedisScript<>(luaScript, Long.class),
                Collections.singletonList(cid + ""));

        // 判断结果。
        if (csave >= 0) {
            // 发送消息到mq中 优惠券id 用户id 抢到的时间。
            Map<String, Object> message = new HashMap<>();
            message.put("uid", UserUtil.getUser().getId());
            message.put("cid", cid);
            message.put("now", new Date().getTime());

            // 放入MQ中。
            eventUtil.publishEvent(EventConstant.EVENT_COUPON_GET, message);

            // 抢劵成功。
            return new ResultData<Integer>().setData(1);
        }

        // 库存不足。
        return new ResultData<Integer>().setData(-2);
    }

}
