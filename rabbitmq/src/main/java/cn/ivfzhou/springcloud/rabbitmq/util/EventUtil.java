package cn.ivfzhou.springcloud.rabbitmq.util;

import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 事件发布工具类。
 * <p>
 * 封装 RabbitTemplate，提供便捷的事件发布方法。
 * 通过指定事件类型（路由键）将消息发送到对应的交换机。
 * </p>
 */

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发布事件消息到 RabbitMQ。
     *
     * @param eventType 事件类型（作为路由键）
     * @param msg       事件消息对象（需实现 Serializable）
     */
    public void publishEvent(String eventType, Object msg) {
        rabbitTemplate.convertAndSend(EventConstant.EXCHANGE_NAME, eventType, msg);
    }

}
