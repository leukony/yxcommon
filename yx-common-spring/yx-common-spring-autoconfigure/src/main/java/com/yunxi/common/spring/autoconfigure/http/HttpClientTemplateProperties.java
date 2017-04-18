package com.yunxi.common.spring.autoconfigure.http;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HttpClientTemplate参数
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpClientTemplateProperties.java, v 0.1 2017年4月18日 下午3:36:03 leukony Exp $
 */
@ConfigurationProperties(HttpClientTemplateProperties.HTTP_CLIENT_TEMPLATE_PREFIX)
public class HttpClientTemplateProperties {
    
    public static final String HTTP_CLIENT_TEMPLATE_PREFIX = "spring.yxcommon.http.client";
    
    /** 应用名 */
    private String appName;

    /** 默认连接数 */
    private int    maxConnPerHost           = 6;

    /** 最大连接数 */
    private int    maxTotalConn             = 10;

    /** 默认等待数据返回超时，单位:毫秒*/
    private int    soTimeout                = 10000;

    /** 默认等待连接建立超时，单位:毫秒*/
    private int    connectionTimeout        = 1000;

    /** 默认请求连接池连接超时,单位:毫秒*/
    private int    connectionManagerTimeout = 1000;
    
    /** 默认请求的编码 */
    private String contentCharset           = "UTF-8";

    /** 代理Host */
    private String proxyHost;

    /** 代理端口 */
    private int    proxyPort;

    /** 代理用户名 */
    private String proxyUserName;

    /** 代理密码 */
    private String proxyUserPassword;

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
      * Getter method for property <tt>maxConnPerHost</tt>.
      * 
      * @return property value of maxConnPerHost
      */
    public int getMaxConnPerHost() {
        return maxConnPerHost;
    }

    /**
      * Setter method for property <tt>maxConnPerHost</tt>.
      * 
      * @param maxConnPerHost value to be assigned to property maxConnPerHost
      */
    public void setMaxConnPerHost(int maxConnPerHost) {
        this.maxConnPerHost = maxConnPerHost;
    }

    /**
      * Getter method for property <tt>maxTotalConn</tt>.
      * 
      * @return property value of maxTotalConn
      */
    public int getMaxTotalConn() {
        return maxTotalConn;
    }

    /**
      * Setter method for property <tt>maxTotalConn</tt>.
      * 
      * @param maxTotalConn value to be assigned to property maxTotalConn
      */
    public void setMaxTotalConn(int maxTotalConn) {
        this.maxTotalConn = maxTotalConn;
    }

    /**
      * Getter method for property <tt>soTimeout</tt>.
      * 
      * @return property value of soTimeout
      */
    public int getSoTimeout() {
        return soTimeout;
    }

    /**
      * Setter method for property <tt>soTimeout</tt>.
      * 
      * @param soTimeout value to be assigned to property soTimeout
      */
    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    /**
      * Getter method for property <tt>connectionTimeout</tt>.
      * 
      * @return property value of connectionTimeout
      */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
      * Setter method for property <tt>connectionTimeout</tt>.
      * 
      * @param connectionTimeout value to be assigned to property connectionTimeout
      */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
      * Getter method for property <tt>connectionManagerTimeout</tt>.
      * 
      * @return property value of connectionManagerTimeout
      */
    public int getConnectionManagerTimeout() {
        return connectionManagerTimeout;
    }

    /**
      * Setter method for property <tt>connectionManagerTimeout</tt>.
      * 
      * @param connectionManagerTimeout value to be assigned to property connectionManagerTimeout
      */
    public void setConnectionManagerTimeout(int connectionManagerTimeout) {
        this.connectionManagerTimeout = connectionManagerTimeout;
    }

    /**
      * Getter method for property <tt>contentCharset</tt>.
      * 
      * @return property value of contentCharset
      */
    public String getContentCharset() {
        return contentCharset;
    }

    /**
      * Setter method for property <tt>contentCharset</tt>.
      * 
      * @param contentCharset value to be assigned to property contentCharset
      */
    public void setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
    }

    /**
      * Getter method for property <tt>proxyHost</tt>.
      * 
      * @return property value of proxyHost
      */
    public String getProxyHost() {
        return proxyHost;
    }

    /**
      * Setter method for property <tt>proxyHost</tt>.
      * 
      * @param proxyHost value to be assigned to property proxyHost
      */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    /**
      * Getter method for property <tt>proxyPort</tt>.
      * 
      * @return property value of proxyPort
      */
    public int getProxyPort() {
        return proxyPort;
    }

    /**
      * Setter method for property <tt>proxyPort</tt>.
      * 
      * @param proxyPort value to be assigned to property proxyPort
      */
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
      * Getter method for property <tt>proxyUserName</tt>.
      * 
      * @return property value of proxyUserName
      */
    public String getProxyUserName() {
        return proxyUserName;
    }

    /**
      * Setter method for property <tt>proxyUserName</tt>.
      * 
      * @param proxyUserName value to be assigned to property proxyUserName
      */
    public void setProxyUserName(String proxyUserName) {
        this.proxyUserName = proxyUserName;
    }

    /**
      * Getter method for property <tt>proxyUserPassword</tt>.
      * 
      * @return property value of proxyUserPassword
      */
    public String getProxyUserPassword() {
        return proxyUserPassword;
    }

    /**
      * Setter method for property <tt>proxyUserPassword</tt>.
      * 
      * @param proxyUserPassword value to be assigned to property proxyUserPassword
      */
    public void setProxyUserPassword(String proxyUserPassword) {
        this.proxyUserPassword = proxyUserPassword;
    }
}