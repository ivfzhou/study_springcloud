package cn.ivfzhou.springcloud.common.constant;

/**
 * 统一响应状态码常量接口。
 * <p>
 * 定义接口返回结果中使用的业务状态码，与 {@link cn.ivfzhou.springcloud.entity.ResultData.Code} 互补，
 * 用于扩展更多的业务状态码。
 * </p>
 */
public interface ResultDataCode {
    /** 优惠券抢购限量已达上限 */
    int RARE_LIMIT_REACHED = 10001;
    /** 需要登录/重新登录 */
    int TO_LOGIN = 10002;
}
