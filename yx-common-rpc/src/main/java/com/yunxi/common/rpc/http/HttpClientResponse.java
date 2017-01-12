package com.yunxi.common.rpc.http;

/**
 * HttpClient响应结果
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpClientResponse.java, v 0.1 2017年1月12日 下午7:06:12 leukony Exp $
 */
public class HttpClientResponse {

    /** 响应结果码 */
    private int    responseCode;
    
    /** 响应结果体 */
    private Object responseBody;

    /**
      * Getter method for property <tt>responseCode</tt>.
      * 
      * @return property value of responseCode
      */
    public int getResponseCode() {
        return responseCode;
    }

    /**
      * Setter method for property <tt>responseCode</tt>.
      * 
      * @param responseCode value to be assigned to property responseCode
      */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
      * Getter method for property <tt>responseBody</tt>.
      * 
      * @return property value of responseBody
      */
    public Object getResponseBody() {
        return responseBody;
    }

    /**
      * Setter method for property <tt>responseBody</tt>.
      * 
      * @param responseBody value to be assigned to property responseBody
      */
    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }
}