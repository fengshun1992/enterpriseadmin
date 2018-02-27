package com.admin.util;

import com.google.common.base.Strings;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return Strings.isNullOrEmpty(str) || "null".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 在字符串左侧按长度填充字符
     *
     * @param s   原字符串
     * @param len 填充后的总字符串长度
     * @param c   填充字符
     * @return 填充完的字符串
     */
    public static String padleft(String s, int len, char c) {
        s = s.trim();
        if (s.length() > len)
            throw new IllegalArgumentException("invalid len " + s.length() + "/" + len);
        StringBuilder d = new StringBuilder(len);
        int fill = len - s.length();
        while (fill-- > 0)
            d.append(c);
        d.append(s);
        return d.toString();
    }
}
