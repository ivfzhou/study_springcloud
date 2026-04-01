package cn.ivfzhou.springcloud.common.constant;

/**
 * Redis 缓存 Key 常量接口。
 * <p>
 * 定义系统中使用的 Redis Key 的命名格式，统一管理缓存键名。
 * </p>
 */
public interface RedisKey {
    /** 优惠券发放库存 Key 格式，%s 为优惠券ID */
    String COUPON_FMT_SET = "coupon_%s";
}
