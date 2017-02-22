package com.yunxi.common.rpc.http;

/**
 * HttpClient代理
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpClientProxy.java, v 0.1 2017年2月22日 下午4:28:15 leukony Exp $
 */
public class HttpClientProxy {

    /** 代理Host */
    private String proxyHost;

    /** 代理端口 */
    private int    proxyPort;

    /** 代理用户名 */
    private String proxyUserName;

    /** 代理密码 */
    private String proxyUserPassword;

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