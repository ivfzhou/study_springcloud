package cn.ivfzhou.springcloud.common.util;

import cn.ivfzhou.springcloud.entity.db.User;

/**
 * 当前登录用户上下文工具类。
 * <p>
 * 基于 ThreadLocal 实现的线程级用户信息存储，在请求处理链路中传递当前登录用户信息。
 * 配合 {@link cn.ivfzhou.springcloud.common.aspect.NeedLoginAop} 切面使用，
 * 切面校验登录后将用户对象存入此处，业务代码可直接获取。
 * </p>
 */

    private static final ThreadLocal<User> user = new ThreadLocal<>();

    public static User getUser() {
        return UserUtil.user.get();
    }

    public static void setUser(User user) {
        UserUtil.user.set(user);
    }

}
