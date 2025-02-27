package cn.ivfzhou.reserve_platform.common.login;

import cn.ivfzhou.reserve_platform.entity.db.User;

public class UserUtil {

    private static final ThreadLocal<User> user = new ThreadLocal<>();

    public static User getUser() {
        return UserUtil.user.get();
    }

    public static void setUser(User user) {
        UserUtil.user.set(user);
    }

}
