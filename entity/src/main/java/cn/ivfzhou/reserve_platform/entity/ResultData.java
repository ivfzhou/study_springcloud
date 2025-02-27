package cn.ivfzhou.reserve_platform.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ResultData<T> implements Serializable {

    // 请求码。
    private int code = Code.OK;
    // 响应的消息。
    private String msg;
    // 响应的数据部分。
    private T data;

    public interface Code {
        int OK = 200;
        int ERROR = 300;
        int TO_LOGIN = 666;
        int MUST_REQUEST = 777;
    }

}
