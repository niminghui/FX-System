package com.shiep.fxauth.utils;

import java.util.UUID;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 9:38
 * @description: UUID工具类
 */
public class UuidTools {
    /**
     * description: 得到32位连续无'-'的UUID字符串
     *
     * @param
     * @return java.lang.String
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        String[] uuids = uuid.split("-");
        String result = "";
        for (String string : uuids) {
            result += string;
        }
        return result;
    }

    /**
     * description: 得到8位的UUID字符串
     *
     * @param
     * @return java.lang.String
     */
    public static String get8BitUUID() {
        String uuid = getUUID();
        String uuid8 = "";
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * 32);
            uuid8 += uuid.substring(index, index + 1);
        }
        return uuid8;
    }
}
