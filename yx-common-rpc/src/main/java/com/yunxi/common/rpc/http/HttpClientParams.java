package com.yunxi.common.rpc.http;

/**
 * HttpClient连接参数
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpClientParams.java, v 0.1 2017年1月13日 上午8:44:57 leukony Exp $
 */
public class HttpClientParams {

    private int maxConnPerHost           = 6;

    private int maxTotalConn             = 10;

    /** 默认等待数据返回超时，单位:毫秒*/
    private int soTimeout                = 10000;

    /** 默认等待连接建立超时，单位:毫秒*/
    private int connectionTimeout        = 1000;

    /** 默认请求连接池连接超时,单位:毫秒*/
    private int connectionManagerTimeout = 1000;

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
}