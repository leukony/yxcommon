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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(long requestSize) {
        this.requestSize = requestSize;
    }

    public long getResponseSize() {
        return responseSize;
    }

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