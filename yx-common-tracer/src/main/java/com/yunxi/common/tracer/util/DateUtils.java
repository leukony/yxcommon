package com.yunxi.common.tracer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: DateUtils.java, v 0.1 2017年1月11日 上午10:54:48 leukony Exp $
 */
public class DateUtils {

    public static final String   DEFAULT_DATE_FORMAT_PATTERN  = "yyyy-MM-dd";
    public static final String   DEFAULT_TIME_FORMAT_PATTERN  = "yyyy-MM-dd HH:mm:ss";
    public static final String   DEFAULT_MILLS_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    
    public static final TimeZone DEFAULT_TIMEZONE             = TimeZone.getTimeZone("GMT");

    /**
     * 日期解析
     * @param dateStr
     * @return
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, DEFAULT_DATE_FORMAT_PATTERN);
    }

    /**
     * 日期解析
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        try {
            DateFormat format = new SimpleDateFormat(pattern);
            // 所有的日期格式化必须在"GMT"时区进行
            format.setTimeZone(DEFAULT_TIMEZONE);
            return format.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期格式化
     * @param dateMills
     * @return
     */
    public static String format(long dateMills) {
        return format(new Date(dateMills), DEFAULT_DATE_FORMAT_PATTERN);
    }
    
    /**
     * 日期格式化
     * @param dateMills
     * @param pattern
     * @return
     */
    public static String format(long dateMills, String pattern) {
        return format(new Date(dateMills), pattern);
    }

    /**
     * 日期格式化
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, DEFAULT_DATE_FORMAT_PATTERN);
    }

    /**
     * 日期格式化
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        // 所有的日期格式化必须在"GMT"时区进行
        format.setTimeZone(DEFAULT_TIMEZONE);
        return format.format(date);
    }
    
    /**
     * 获取当前时间
     */
    public static Date currentDate() {
        return new Date();
    }
    
    /**
     * 获取当前时间戳
     * @return
     */
    public static long currentMills() {
        return System.currentTimeMillis();
    }
}