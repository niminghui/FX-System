package com.shiep.fxauth.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 倪明辉
 * @date: 2019/4/16 9:49
 * @description: IP工具类
 */
public class IpUtils {
    private static final String UNKONWN = "unknown";
    private static final String COMMA = ",";

    /**
     * description: 获取客户端的IP地址
     *
     * @param request 请求
     * @return java.lang.String
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKONWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKONWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKONWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(COMMA)) {
            return ip.split(COMMA)[0];
        } else {
            return ip;
        }
    }
}
