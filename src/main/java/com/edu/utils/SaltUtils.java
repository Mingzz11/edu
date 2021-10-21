package com.edu.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class SaltUtils {
    /**
     * 生成salt(随机盐)的静态方法
     * @param n
     * @return
     */
    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOPQSTUVWXYZabcdefghijklmnopqstuvwxyz01234567890!@#$%^&*()".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            stringBuilder.append(aChar);
        }
        return stringBuilder.toString();
    }

    /**
     * 字符串转换
     * @param string
     * @return
     */
    public static Integer changeString(String string){
        if (string != null && !StringUtils.isBlank(string)) {
            return Integer.valueOf(string);
        }else {
            return null;
        }
    }
}
