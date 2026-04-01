package cn.ivfzhou.springcloud.rabbitmq.config;

import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import cn.ivfzhou.springcloud.rabbitmq.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * RabbitMQ 事件消费端配置类。
 * <p>
 * 自动为当前服务创建消息队列，并将队列与交换机通过事件类型（路由键）进行绑定。
 * 通过扫描所有 {@link EventListener} 实现类，动态创建绑定关系。
 * 仅在容器中存在 EventListener 实现类时生效（@ConditionalOnBean）。
 * </p>
 */

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private SpringContextUtil springContextUtil;

    @Autowired
    private List<EventListener> eventListeners;

    /**
     * 创建消息队列。
     * 队列名称格式：{应用名}-queue，持久化、非独占、非自动删除。
     *
     * @return 队列对象
     */
    @Bean
    public Queue getQueue() {
        return new Queue(applicationName + "-queue", true, false, false);
    }

    /**
     * 队列和交换机的绑定，并塞入 Spring 中。
     */
    @Bean
    public Binding getBinding(final Queue queue, final DirectExchange exchange) {
        // 循环所有的 EventListener 实现类。
        eventListeners.forEach(event -> {
            // 获得当前的处理器需要处理的事件类型 - 路由键。
            String eventType = event.getEventType();
            log.info("绑定的路由键：{}", eventType);
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(eventType);
            // 动态将 binding 对象注册到 Spring 容器中。
            springContextUtil.registerBean(eventType + event.hashCode(), binding);
        });

        return null;
    }

}
