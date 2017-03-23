package com.charlie.weibo.constant;

/**
 * Created by dhy on 17-3-22.
 *
 */
public class KeyGenerator {
    public final static String USER_ID = "weibo::user::id";
    public final static String USER_NAME = "weibo::user::name";

    private final static String USER_KEY = "weibo::user::%d";

    public static String generateUserKey(Long userId) {
        return String.format(USER_KEY, userId);
    }
}
