package cn.ivfzhou.springcloud.rabbitmq.util;

import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishEvent(String eventType, Object msg) {
        rabbitTemplate.convertAndSend(EventConstant.EXCHANGE_NAME, eventType, msg);
    }

}
