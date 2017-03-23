package com.charlie.weibo.keys;

/**
 * Created by dhy on 17-3-22.
 *
 */
public class KeyGenerator {
    public final static String USER_ID = "weibo::user::id";
    public final static String USER_NAME = "weibo::user::name";

    // key 为 username, value为用户ID
    public final static String USER_NAME_TO_ID = "weibo::user::name::id";

    private final static String USER_KEY = "weibo::user::%d";

    public static String generateUserKey(Long userId) {
        return String.format(USER_KEY, userId);
    }
}
