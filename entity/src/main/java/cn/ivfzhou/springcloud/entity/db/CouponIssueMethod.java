package cn.ivfzhou.springcloud.entity.db;

/** 优惠券发放方式枚举。CENTER-领券中心领取，BUY-购买后赠送 */
public enum CouponIssueMethod {
    /** 领券中心领取 */
    CENTER(0),
    /** 购买后赠送 */
    BUY(1);

    /** 发放方式的数值编码 */
    public final Integer value;

    CouponIssueMethod(Integer value) {
        this.value = value;
    }
}
