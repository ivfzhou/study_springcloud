package cn.ivfzhou.reserve_platform.event;

/**
 * 事件监听器的接口。
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
