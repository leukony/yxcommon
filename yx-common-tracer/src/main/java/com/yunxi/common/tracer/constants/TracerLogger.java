package com.yunxi.common.tracer.constants;

import com.yunxi.common.tracer.appender.TimedRollingFileAppender;

/**
 * Tracer日志
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerLogger.java, v 0.1 2017年1月12日 下午4:05:24 leukony Exp $
 */
public enum TracerLogger {

    HTTP_CLIENT_DIGEST("http_client_digest", "httpclient-digest.log",
                       TimedRollingFileAppender.HOURLY_ROLLING_PATTERN,
                       TimedRollingFileAppender.DEFAULT_LOG_RESERVE_DAY),

    HTTP_SERVER_DIGEST("http_server_digest", "httpserver-digest.log",
                       TimedRollingFileAppender.HOURLY_ROLLING_PATTERN,
                       TimedRollingFileAppender.DEFAULT_LOG_RESERVE_DAY),

    ;

    /** 日志名称 */
    private String name;
    /** 日志文件 */
    private String fileName;
    /** 日志文件滚动格式 */
    private String pattern;
    /** 日志文件暴露天数 */
    private int    reserve;

    TracerLogger(String name, String fileName, String pattern, int reserve) {
        this.name = name;
        this.fileName = fileName;
        this.pattern = pattern;
        this.reserve = reserve;
    }

    /**
      * Getter method for property <tt>name</tt>.
      * 
      * @return property value of name
      */
    public String getName() {
        return name;
    }

    /**
      * Setter method for property <tt>name</tt>.
      * 
      * @param name value to be assigned to property name
      */
    public void setName(String name) {
        this.name = name;
    }

    /**
      * Getter method for property <tt>fileName</tt>.
      * 
      * @return property value of fileName
      */
    public String getFileName() {
        return fileName;
    }

    /**
      * Setter method for property <tt>fileName</tt>.
      * 
      * @param fileName value to be assigned to property fileName
      */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
      * Getter method for property <tt>pattern</tt>.
      * 
      * @return property value of pattern
      */
    public String getPattern() {
        return pattern;
    }

    /**
      * Setter method for property <tt>pattern</tt>.
      * 
      * @param pattern value to be assigned to property pattern
      */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
      * Getter method for property <tt>reserve</tt>.
      * 
      * @return property value of reserve
      */
    public int getReserve() {
        return reserve;
    }

    /**
      * Setter method for property <tt>reserve</tt>.
      * 
      * @param reserve value to be assigned to property reserve
      */
    public void setReserve(int reserve) {
        this.reserve = reserve;
    }
}