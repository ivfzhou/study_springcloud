package cn.ivfzhou.springcloud.common.constant;

/**
 * 系统通用常量接口。
 * <p>
 * 定义系统中使用的通用常量值，如 JWT Token 中存储用户信息的 key 名称等。
 * </p>
 */
public interface Consts {
    /** JWT Token 中存储用户ID的 key */
    String UID_KEY = "uid";
    /** JWT Token 中存储用户名的 key */
    String USERNAME_KEY = "user";
}
