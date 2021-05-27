package com.yy.security.user;

import java.util.Optional;

/**
 * @Author ywl
 * @Date 2021/5/26 20:00
 * @Description 用户信息
 */
public class CurrentUserHolder {
    private static final ThreadLocal<UserInfo> USER_THREAD_LOCAL = new ThreadLocal();

    public CurrentUserHolder() {
    }

    public static void set(UserInfo user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static UserInfo get() {
        return Optional.ofNullable(USER_THREAD_LOCAL.get()).orElseThrow(() -> new RuntimeException("获取当前用户信息为空！"));
    }

    public static void remove() {
        USER_THREAD_LOCAL.remove();
    }
}
