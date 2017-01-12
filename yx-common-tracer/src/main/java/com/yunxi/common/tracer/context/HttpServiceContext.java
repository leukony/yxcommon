package com.yunxi.common.tracer.context;

/**
 * Http服务日志上下文
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpServiceContext.java, v 0.1 2017年1月9日 下午3:32:03 leukony Exp $
 */
public class HttpServiceContext extends TracerContext<HttpServiceContext> {

    /** 请求的Url */
    private String url;
    /** 请求的Method, 比如：GET、POST */
    private String method;
    /** 请求的大小 */
    private long   requestSize;
    /** 响应的大小 */
    private long   responseSize;
    
    /**
      * Getter method for property <tt>url</tt>.
      * 
      * @return property value of url
      */
    public String getUrl() {
        return url;
    }

    /**
      * Setter method for property <tt>url</tt>.
      * 
      * @param url value to be assigned to property url
      */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
      * Getter method for property <tt>method</tt>.
      * 
      * @return property value of method
      */
    public String getMethod() {
        return method;
    }

    /**
      * Setter method for property <tt>method</tt>.
      * 
      * @param method value to be assigned to property method
      */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
      * Getter method for property <tt>requestSize</tt>.
      * 
      * @return property value of requestSize
      */
    public long getRequestSize() {
        return requestSize;
    }

    /**
      * Setter method for property <tt>requestSize</tt>.
      * 
      * @param requestSize value to be assigned to property requestSize
      */
    public void setRequestSize(long requestSize) {
        this.requestSize = requestSize;
    }

    /**
      * Getter method for property <tt>responseSize</tt>.
      * 
      * @return property value of responseSize
      */
    public long getResponseSize() {
        return responseSize;
    }

    /**
      * Setter method for property <tt>responseSize</tt>.
      * 
      * @param responseSize value to be assigned to property responseSize
      */
    public void setResponseSize(long responseSize) {
        this.responseSize = responseSize;
    }

    /** 
     * @see com.yunxi.common.tracer.context.TracerContext#clone()
     */
    @Override
    public HttpServiceContext clone() {
        HttpServiceContext httpServiceContext = new HttpServiceContext();
        httpServiceContext.url = this.url;
        httpServiceContext.method = this.method;
        httpServiceContext.requestSize = this.requestSize;
        httpServiceContext.responseSize = this.responseSize;
        return super.clone(httpServiceContext);
    }
}