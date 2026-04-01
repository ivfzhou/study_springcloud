package cn.ivfzhou.springcloud.rabbitmq;

/**
 * 事件监听器接口。
 * <p>
 * 所有需要监听 RabbitMQ 事件的组件都需要实现此接口。
 * 通过 getEventType() 方法指定监听的事件类型（路由键），
 * eventHandler() 方法定义事件到达时的处理逻辑。
 * </p>
 *
 * @param <T> 事件消息的数据类型
 */
public interface EventListener<T> {

    /**
     * 监听的事件类型。
     */
    String getEventType();

    /**
     * 事件处理方法。
     */
    void eventHandler(T msg);

}
