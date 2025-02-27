package cn.ivfzhou.reserve_platform.event.util;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.ivfzhou.reserve_platform.event.constant.EventConstant;

@Component
public class EventUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishEvent(String eventType, Object msg) {
        rabbitTemplate.convertAndSend(EventConstant.EXCHANGE_NAME, eventType, msg);
    }

}
