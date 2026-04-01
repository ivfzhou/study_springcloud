package cn.ivfzhou.springcloud.rabbitmq.config;

import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 事件发布端/消费端公共配置类。
 * <p>
 * 创建 Direct 类型的交换机，事件发布者和事件接收者都需要此交换机。
 * 交换机名称由 {@link cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant#EXCHANGE_NAME} 定义。
 * </p>
 */

    /**
     * 事件发布者需要创建。
     * 事件接收者也需要创建。
     * 创建了一个交换机。
     */
    @Bean
    public DirectExchange getExchange() {
        return new DirectExchange(EventConstant.EXCHANGE_NAME, true, false);
    }
}
