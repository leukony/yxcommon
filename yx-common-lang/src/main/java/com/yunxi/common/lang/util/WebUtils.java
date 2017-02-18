package com.yunxi.common.lang.util;

import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WEB工具类
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: WebUtils.java, v 0.1 2017年2月17日 下午1:50:12 leukony Exp $
 */
public class WebUtils {

    public static final String AND      = "&";
    public static final String EQUAL    = "=";
    public static final String QUESTION = "?";

    /**
     * 获取Cookie中变量的值
     * @param request
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {
        if (name == null || name.trim().length() == 0) {
            return null;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    /**
     * 设置Cookie中变量的值
     * @param response
     * @param name
     * @param value
     * @param domain
     * @param path
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response, String name, String value,
                                 String domain, String path, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取Parameter中变量的值
     * @param request
     * @param key
     * @return
     */
    public static String getRequestParameter(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }

    /**
     * 获取Parameter中变量的值, 若为空，则返回默认
     * @param request
     * @param key
     * @param defValue
     * @return
     */
    public static String getRequestParameter(HttpServletRequest request, String key, String defValue) {
        String value = getRequestParameter(request, key);
        return value == null ? defValue : value;
    }

    /**
     * 获取Attribute中变量的值
     * @param request
     * @param key
     * @return
     */
    public static Object getRequestAttribute(HttpServletRequest request, String key) {
        return request.getAttribute(key);
    }

    /**
     * 获取Attribute中变量的值, 若为空，则返回默认
     * @param request
     * @param key
     * @param defValue
     * @return
     */
    public static Object getRequestAttribute(HttpServletRequest request, String key, Object defValue) {
        Object value = getRequestAttribute(request, key);
        return value == null ? defValue : value;
    }

    /**
     * 设置Attribute中变量的值
     * @param request
     * @param key
     * @param value
     */
    public static void setRequestAttribute(HttpServletRequest request, String key, Object value) {
        if ((request != null) && (key != null && key.trim().length() > 0)) {
            request.setAttribute(key, value);
        }
    }

    /**
     * 获取Session中变量的值
     * @param request
     * @param key
     * @return
     */
    public static Object getSessionAttribute(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    /**
     * 获取Session中变量的值, 若为空，则返回默认
     * @param request
     * @param key
     * @param defValue
     * @return
     */
    public static Object getSessionAttribute(HttpServletRequest request, String key, Object defValue) {
        Object value = getSessionAttribute(request, key);
        return value == null ? defValue : value;
    }

    /**
     * 设置Session中变量的值
     * @param request
     * @param key
     * @param value
     */
    public static void setSessionAttribute(HttpServletRequest request, String key, Object value) {
        if ((request != null) && (key != null && key.trim().length() > 0)) {
            request.getSession().setAttribute(key, value);
        }
    }

    /**
     * 取得请求的URL
     * @param request
     * @return
     */
    public static String getRequestURL(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    /**
     * 取得请求的URL和参数
     * @param request
     * @return
     */
    public static String getRequestURLWithParameters(HttpServletRequest request) {
        String url = getRequestURL(request);
        String query = getRequestParameters(request);

        if (query != null && query.trim().length() > 0) {
            return url + QUESTION + query;
        } else {
            return url;
        }
    }

    /**
     * 取得请求的参数拼接成字符串
     * @param request
     * @return
     */
    public static String getRequestParameters(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        String inputCharset = request.getCharacterEncoding();
        inputCharset = inputCharset == null ? "UTF-8" : inputCharset;
        Enumeration<?> paramEnums = request.getParameterNames();
        while (paramEnums.hasMoreElements()) {
            String paramName = (String) paramEnums.nextElement();
            String paramValue = request.getParameter(paramName);

            try {
                if (paramName != null && paramName.trim().length() > 0) {
                    buffer.append(paramName);
                    buffer.append(EQUAL);
                    buffer.append(URLEncoder.encode(paramValue, inputCharset));
                    buffer.append(AND);
                }
            } catch (Exception e) {
                // ignore
            }
        }

        if (buffer.length() > 0) {
            return buffer.substring(0, buffer.length() - 1);
        }

        return null;
    }
}