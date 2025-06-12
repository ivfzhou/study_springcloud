package cn.ivfzhou.springcloud.entity.db;

public enum CouponIssueMethod {
    CENTER(0), BUY(1);

    public final Integer value;

    CouponIssueMethod(Integer value) {
        this.value = value;
    }
}
