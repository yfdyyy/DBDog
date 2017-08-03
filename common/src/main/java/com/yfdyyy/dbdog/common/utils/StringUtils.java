package com.yfdyyy.dbdog.common.utils;

/**
 * Created by Julie on 2017/8/2.
 */
public class StringUtils {
    public static boolean isBlank(String str) {
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
