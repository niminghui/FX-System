package com.shiep.fxauth.utils;

import java.sql.Timestamp;

/**
 * @author: 倪明辉
 * @date: 2019/4/25 15:07
 * @description: Timestamp工具类
 */
public class TimestampUtils {
    private static String year;
    private static String month;
    private static String day;
    private static String hour;
    private static String minute;
    private static String second;

    /**
     * description: 解析前台input类型为datetime-local的String为Timestamp
     *
     * @param datetime datetime-local的String
     * @return java.sql.Timestamp
     */
    public static Timestamp parse(String datetime) {
        if (datetime.isEmpty()) {
            return null;
        }
        String[] str = datetime.split("T");
        String[] date = str[0].split("-");
        year = date[0];
        month = date[1];
        day = date[2];
        String[] time = str[1].split(":");
        hour = time[0];
        minute = time[1];
        second = "00";
        StringBuffer buffer = new StringBuffer();
        buffer.append(year);
        buffer.append("-");
        buffer.append(month);
        buffer.append("-");
        buffer.append(day);
        buffer.append(" ");
        buffer.append(hour);
        buffer.append(":");
        buffer.append(minute);
        buffer.append(":");
        buffer.append(second);
        return Timestamp.valueOf(buffer.toString());
    }
}
