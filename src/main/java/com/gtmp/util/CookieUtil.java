package com.gtmp.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static String getValue(HttpServletRequest request, String key) {
        if (request == null || key == null || "".equals(key)) {
            throw new IllegalArgumentException("参数为空!");
        }

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0)
            return null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key))
                return cookie.getValue();
        }

        return null;
    }

    public static void add(HttpServletResponse response, String key, String value, String path, int expiredSecond) {
        if (response == null || key == null || value == null || "".equals(key)){
            throw new IllegalArgumentException("参数为空!");
        }

        Cookie cookie = new Cookie(key, value);
        cookie.setPath(path);
        cookie.setMaxAge(expiredSecond);
        response.addCookie(cookie);
    }

    public static void del(HttpServletResponse response, String key) {
        if (response == null || key == null || "".equals(key)) {
            throw new IllegalArgumentException("参数为空!");
        }

        Cookie cookie = new Cookie(key, "null");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
