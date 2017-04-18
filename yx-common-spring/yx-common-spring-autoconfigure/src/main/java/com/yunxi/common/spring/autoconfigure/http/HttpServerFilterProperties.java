package com.yunxi.common.spring.autoconfigure.http;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HttpServerFilter参数
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpServerFilterProperties.java, v 0.1 2017年4月18日 下午4:19:50 leukony Exp $
 */
@ConfigurationProperties(HttpServerFilterProperties.HTTP_SERVER_FILTER_PREFIX)
public class HttpServerFilterProperties {
    
    public static final String HTTP_SERVER_FILTER_PREFIX = "spring.yxcommon.http.filter";

    /** 应用名 */
    private String appName;

    /** 忽略的URL */
    private String excludeUrl;

    /**
      * Getter method for property <tt>appName</tt>.
      * 
      * @return property value of appName
      */
    public String getAppName() {
        return appName;
    }

    /**
      * Setter method for property <tt>appName</tt>.
      * 
      * @param appName value to be assigned to property appName
      */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
      * Getter method for property <tt>excludeUrl</tt>.
      * 
      * @return property value of excludeUrl
      */
    public String getExcludeUrl() {
        return excludeUrl;
    }

    /**
      * Setter method for property <tt>excludeUrl</tt>.
      * 
      * @param excludeUrl value to be assigned to property excludeUrl
      */
    public void setExcludeUrl(String excludeUrl) {
        this.excludeUrl = excludeUrl;
    }
}