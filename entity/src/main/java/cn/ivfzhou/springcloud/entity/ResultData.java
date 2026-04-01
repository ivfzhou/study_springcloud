package cn.ivfzhou.springcloud.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统一响应结果封装类。
 * <p>
 * 所有接口的返回值都使用此类进行统一封装，包含请求状态码、响应消息和响应数据。
 * 使用泛型 T 支持任意类型的响应数据。
 * </p>
 *
 * @param <T> 响应数据的类型
 */
@Data
@Accessors(chain = true)
public class ResultData<T> implements Serializable {

    // 请求码。
    private int code = Code.OK;
    // 响应的消息。
    private String msg;
    // 响应的数据部分。
    private T data;

    /** 响应状态码常量定义。 */
    public interface Code {
        /** 成功状态码 */
        int OK = 200;
        /** 错误状态码 */
        int ERROR = 300;
        /** 需要登录状态码 */
        int TO_LOGIN = 666;
    }

}
