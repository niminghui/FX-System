package com.shiep.fxauth.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/4/9 10:12
 * @description: cookie工具类
 */
public class CookieUtils {
    private static final int COOKIE_MAX_AGE = 7 * 24 * 3600;

    /**
     * description: 根据名字获取cookie
     *
     * @param request    请求
     * @param cookieName cookie名字
     * @return javax.servlet.http.Cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Map<String, Cookie> cookieMap = readCookieToMap(request);
        if (cookieMap.containsKey(cookieName)) {
            return cookieMap.get(cookieName);
        } else {
            return null;
        }
    }

    /**
     * description: 添加一条新的Cookie，可以指定过期时间(单位：秒)
     *
     * @param cookieName     cookie名
     * @param cookieValue    cookie值
     * @param expirationTime 过期时间(单位：秒)
     * @return javax.servlet.http.Cookie
     */
    public static Cookie setCookie(String cookieName, String cookieValue, int expirationTime) {
        if (null == cookieName) {
            return null;
        }
        if (null == cookieValue) {
            cookieValue = "";
        }
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        if (expirationTime != 0) {
            cookie.setMaxAge(expirationTime);
        } else {
            cookie.setMaxAge(COOKIE_MAX_AGE);
        }
        return cookie;
    }

    /**
     * description: 删除cookie
     *
     * @param request    请求
     * @param cookieName cookie名
     * @return javax.servlet.http.Cookie
     */
    public static Cookie removeCookie(HttpServletRequest request, String cookieName) {
        if (null == cookieName) {
            return null;
        }
        Cookie cookie = getCookie(request, cookieName);
        if (null != cookie) {
            cookie.setPath("/");
            cookie.setValue("");
            cookie.setMaxAge(0);
            return cookie;
        }
        return null;
    }

    /**
     * description: 将cookie封装到Map里面
     *
     * @param request 请求
     * @return java.util.Map<java.lang.String       ,       javax.servlet.http.Cookie>
     */
    private static Map<String, Cookie> readCookieToMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>(16);
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

}
