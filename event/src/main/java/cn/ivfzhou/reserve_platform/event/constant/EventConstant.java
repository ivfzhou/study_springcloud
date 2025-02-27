package cn.ivfzhou.reserve_platform.event.constant;

public interface EventConstant {

    /**
     * 交换机的名称
     */
    String EXCHANGE_NAME = "event-exchange";

    /**
     * 事件类型常量
     */
    // 用户登录事件。
    String EVENT_LOGIN = "login";

    // 酒店新增事件。
    String EVENT_HOTEL_INSERT = "hotel_insert";

    // 酒店客房新增事件。
    String EVENT_HOTEL_ROOM_INSERT = "hotel_room_insert";

    // 酒店价格对象修改事件。
    String EVENT_HOTEL_PRICE_UPDATE = "hotel_price_update";

    // 酒店点击率事件。
    String EVENT_HOTEL_CLICK = "hotel_click";

    // 酒店房间修改事件。
    String EVENT_HOTEL_ROOM_UPDATE = "hotel_room_update";

    // 优惠券抢劵的事件。
    String EVENT_COUPON_GET = "coupon_purchase";

}
