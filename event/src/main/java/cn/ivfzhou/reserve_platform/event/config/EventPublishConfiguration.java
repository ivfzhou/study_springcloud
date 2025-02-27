package cn.ivfzhou.reserve_platform.event.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.ivfzhou.reserve_platform.event.constant.EventConstant;

@Configuration
public class EventPublishConfiguration {

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
