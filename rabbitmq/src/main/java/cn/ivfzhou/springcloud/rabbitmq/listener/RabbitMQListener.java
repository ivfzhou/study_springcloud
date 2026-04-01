package cn.ivfzhou.springcloud.rabbitmq.listener;

import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RabbitMQ 消息监听器。
 * <p>
 * 监听当前服务的消息队列，接收消息后根据路由键（事件类型）分发到
 * 对应的 {@link EventListener} 实现类进行处理。
 * 消息体使用 Java 序列化传输。
 * 仅在容器中存在 EventListener 实现类时生效。
 * </p>
 */

    @Autowired
    private List<EventListener> eventListeners;

    /**
     * 监听消息队列并分发事件处理。
     * <p>
     * 监听的队列为 {spring.application.name}-queue，
     * 根据消息的路由键匹配对应的 EventListener 进行处理。
     * </p>
     *
     * @param message RabbitMQ 消息对象
     */
    @RabbitListener(queues = "${spring.application.name}-queue")
    public void msgHandler(Message message) {
        // 获得发布消息的路由键 - 事件类型。
        final String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        // 交给对应的 EventListener 处理。
        eventListeners.forEach(event -> {
            // 判断事件类型和路由键是否匹配。
            if (event.getEventType().equals(routingKey)) {
                // 直接调用当前 event 处理该消息。
                byte[] msgBytes = message.getBody();
                event.eventHandler(SerializationUtils.deserialize(msgBytes));
            }
        });
    }

}
