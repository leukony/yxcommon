package com.yunxi.common.lang.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: DateUtils.java, v 0.1 2014年5月12日 上午10:54:48 leukony Exp $
 */
public class DateUtils {

    public static final String   DATE_FORMAT_PATTERN         = "yyyy-MM-dd";
    public static final String   TIME_FORMAT_PATTERN         = "yyyy-MM-dd HH:mm:ss";
    public static final String   MILLS_FORMAT_PATTERN        = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String   DATE_FORMAT_PATTERN_SIMPLE  = "yyyyMMdd";
    public static final String   TIME_FORMAT_PATTERN_SIMPLE  = "yyyyMMddHHmmss";
    public static final String   MILLS_FORMAT_PATTERN_SIMPLE = "yyyyMMddHHmmssSSS";

    public static final String   DATE_FORMAT_PATTERN_CHINESE = "yyyy年MM月dd日";
    public static final String   TIME_FORMAT_PATTERN_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final TimeZone DEFAULT_TIMEZONE            = TimeZone.getTimeZone("GMT");

    /**
     * 将字符串转换成日期
     * <p>默认使用"yyyy-MM-dd"转换</p>
     * <p>转换异常直接返回Null</p>
     * @param dateStr
     * @return
     */
    public static Date parse(String dateStr) throws ParseException {
        return parse(dateStr, DATE_FORMAT_PATTERN);
    }

    /**
     * 将字符串转换成日期
     * <p>转换异常直接返回Null</p>
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
     * 将时间格式化
     * <p>默认使用"yyyy-MM-dd"格式化</p>
     * @param dateMills
     * @return
     */
    public static String format(long dateMills) {
        return format(new Date(dateMills), DATE_FORMAT_PATTERN);
    }

    /**
     * 将时间格式化
     * @param dateMills
     * @param pattern
     * @return
     */
    public static String format(long dateMills, String pattern) {
        return format(new Date(dateMills), pattern);
    }

    /**
     * 将时间格式化
     * <p>默认使用"yyyy-MM-dd"格式化</p>
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, DATE_FORMAT_PATTERN);
    }

    /**
     * 将时间格式化
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
     * 按秒偏移
     * @param source
     * @param seconds
     * @return
     */
    public static Date addSeconds(Date source, int seconds) {
        return addDate(source, Calendar.SECOND, seconds);
    }

    /**
     * 按分钟偏移
     * @param source
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date source, int minutes) {
        return addDate(source, Calendar.MINUTE, minutes);
    }

    /**
     * 按小时偏移
     * @param source
     * @param hours
     * @return
     */
    public static Date addHours(Date source, int hours) {
        return addDate(source, Calendar.HOUR, hours);
    }

    /**
     * 按日偏移
     * @param source
     * @param days
     * @return
     */
    public static Date addDays(Date source, int days) {
        return addDate(source, Calendar.DAY_OF_MONTH, days);
    }

    /**
     * 按月偏移
     * @param source
     * @param months
     */
    public static Date addMonths(Date source, int months) {
        return addDate(source, Calendar.MONTH, months);
    }

    /**
     * 按年偏移
     * @param source
     * @param years
     */
    public static Date addYears(Date source, int years) {
        return addDate(source, Calendar.YEAR, years);
    }

    /**
     * 时间偏移
     * @param date
     * @param field
     * @param amount
     * @return
     */
    public static Date addDate(Date date, int field, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, amount);
        return c.getTime();
    }

    /**
     * 计算两个日志相差天数
     * @param first
     * @param last
     */
    public static int diffDays(Date first, Date last) {
        long diffMills = last.getTime() - first.getTime();
        long diffDays = diffMills / (24 * 60 * 60 * 1000);
        return new Long(diffDays).intValue();
    }

    /**
     * 计算两个日志相差月数
     * @param first
     * @param last
     */
    public static int diffMonths(Date first, Date last) {
        int monthCount = 0;
        Calendar before = Calendar.getInstance();
        before.setTime(first);
        Calendar after = Calendar.getInstance();
        after.setTime(last);
        monthCount = (after.get(Calendar.YEAR) - before.get(Calendar.YEAR)) * 12
                     + after.get(Calendar.MONTH) - before.get(Calendar.MONTH);
        return monthCount;
    }

    /**
     * 计算两个日志相差年数
     * @param first
     * @param last
     */
    public static int diffYears(Date first, Date last) {
        int yearCount = 0;
        Calendar before = Calendar.getInstance();
        before.setTime(first);
        Calendar after = Calendar.getInstance();
        after.setTime(last);
        yearCount = after.get(Calendar.YEAR) - before.get(Calendar.YEAR);
        return yearCount;
    }

    /**
     * 获取这月的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取这月的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取月份
     * @param date
     * @return
     */
    public static int getMonths(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取年份
     * @param date
     * @return
     */
    public static int getYeahs(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间
     * @return
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