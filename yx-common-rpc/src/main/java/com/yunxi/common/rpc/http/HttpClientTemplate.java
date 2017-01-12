package com.yunxi.common.rpc.http;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;

import com.yunxi.common.tracer.TracerFactory;
import com.yunxi.common.tracer.constants.TracerConstants;
import com.yunxi.common.tracer.context.HttpServiceContext;
import com.yunxi.common.tracer.tracer.HttpServiceTracer;

/**
 * HttpClient模板
 * 
 * <p>
 * 基于Apache Common HttpClient:
 * <ul>
 * <li>设置默认超时参数</li>
 * <li>执行Http请求</li>
 * <li>断开连接</li>
 * </ul>
 * <p>
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpClientTemplate.java, v 0.1 2017年1月12日 上午9:25:11 leukony Exp $
 */
public class HttpClientTemplate {

    private HttpClient httpClient;
    
    public void initialize() {
        HttpConnectionManagerParams httpClientParam = new HttpConnectionManagerParams();
        httpClientParam.setDefaultMaxConnectionsPerHost(maxConnPerHost);
        httpClientParam.setConnectionTimeout(connectionTimeout);
        httpClientParam.setMaxTotalConnections(maxTotalConn);
        httpClientParam.setSoTimeout(soTimeout);
        
        HttpConnectionManager httpClientManager = new MultiThreadedHttpConnectionManager();
        httpClientManager.setParams(httpClientParam);
        
        httpClient = new HttpClient(httpClientManager);
        httpClient.getParams().setConnectionManagerTimeout(connectionManagerTimeout);
    }

    /**
     * 以Get方式执行http请求
     * @param url
     * @return ResponseBody
     * @throws IOException
     * @throws HttpException
     */
    public String executeGet(final String url) throws IOException, HttpException {
        return executeGet(url, null);
    }

    /**
     * 以Get方式执行http请求
     * @param url
     * @return ResponseBody
     * @throws IOException
     * @throws HttpException
     */
    public String executeGet(final String url, final HttpState state) throws IOException, HttpException {
        return executeWithState(new GetMethod(url), state);
    }

    /**
     * 以Post方式执行http请求
     * @param url
     * @param params
     * @param headerParams
     * @return ResponseBody
     * @throws IOException
     * @throws HttpException
     */
    public String executePost(final String url, final NameValuePair[] params, final NameValuePair[] headerParams) throws IOException, HttpException {
        return executePost(url, params, headerParams, null);
    }

    /**
     * 以Post方式执行http请求
     * @param url
     * @param params
     * @param headerParams
     * @return ResponseBody
     * @throws IOException
     * @throws HttpException
     */
    public String executePost(final String url, final NameValuePair[] params, final NameValuePair[] headerParams, final HttpState httpState) throws IOException, HttpException {
        PostMethod postMethod = new PostMethod(url);
        for (int i = 0; null != headerParams && i < headerParams.length; i++) {
            NameValuePair nameValuePair = headerParams[i];
            postMethod.addRequestHeader(nameValuePair.getName(), nameValuePair.getValue());
        }
        postMethod.addParameters(params);
        return executeWithState(postMethod, httpState);
    }

    /**
     * 传入HttpMethod以执行http请求
     * @param httpMethod
     * @return ResponseBody
     * @throws IOException
     * @throws HttpException
     */
    public String execute(final HttpMethod httpMethod) throws IOException, HttpException {
        return executeWithState(httpMethod, null);
    }

    /**
     * 传入HttpMethod以执行http请求
     * @param httpMethod
     * @param httpState
     * @return ResponseBody
     * @throws IOException
     * @throws HttpException
     */
    public String executeWithState(final HttpMethod httpMethod, final HttpState state) throws IOException, HttpException {
        HttpClientResponse resp = execute(httpMethod, state, new HttpClientCallback<String>() {
            public String process(HttpMethod method) throws IOException {
                return method.getResponseBodyAsString();
            }
        });

        return (String) resp.getResponseBody();
    }

    /**
     * 使用HttpClientCallback处理HttpClient的响应
     * @param httpMethod
     * @param httpClientCallback
     * @return HttpClientResponse
     * @throws IOException
     * @throws HttpException
     */
    public HttpClientResponse execute(final HttpMethod httpMethod, final HttpClientCallback<?> httpClientCallback) throws IOException, HttpException {
        return execute(httpMethod, null, httpClientCallback);
    }

    /**
     * 使用HttpClientCallback处理HttpClient的响应
     * @param httpMethod
     * @param httpState 
     * @param httpClientCallback
     * @return HttpClientResponse
     * @throws IOException
     * @throws HttpException
     */
    public HttpClientResponse execute(final HttpMethod httpMethod, final HttpState httpState, final HttpClientCallback<?> httpClientCallback) throws IOException, HttpException {
        String responseString = "";
        HttpServiceTracer httpServiceTracer = TracerFactory.getHttpClientTracer();

        try {
            HttpServiceContext httpServiceContext = httpServiceTracer.startInvoke();
            if (httpServiceContext != null) {
                URI uri = httpMethod.getURI();
                StringBuilder sb = new StringBuilder();
                int port = uri.getPort();
                String portString = "";

                if (port != -1) {
                    portString = ":" + port;
                }
                
                sb.append(uri.getScheme()).append("://").append(uri.getHost()).append(portString).append(uri.getPath());
                
                httpServiceContext.setUrl(sb.toString());
                httpServiceContext.setMethod(httpMethod.getName());
                if (httpMethod instanceof EntityEnclosingMethod) {
                    RequestEntity requestEntity = ((EntityEnclosingMethod) httpMethod).getRequestEntity();
                    if (requestEntity != null) {
                        httpServiceContext.setRequestSize(requestEntity.getContentLength());
                    }
                }
                httpServiceContext.setCurrentApp(currentApp);

                String traceId = httpServiceContext.getTraceId();
                String traceIndex = httpServiceContext.getTraceIndex();
                if (StringUtils.isNotBlank(traceId) && StringUtils.isNotBlank(traceIndex)) {
                    httpMethod.setRequestHeader(TracerConstants.TRACE_ID, traceId);
                    httpMethod.setRequestHeader(TracerConstants.TRACE_INDEX, traceIndex);
                }
            }
            
            int responseCode = this.httpClient.executeMethod(null, httpMethod, httpState);
            
            HttpClientResponse response = new HttpClientResponse();
            response.setResponseCode(responseCode);

            if (httpServiceContext != null) {
                if (httpMethod instanceof HttpMethodBase) {
                    httpServiceContext.setResponseSize(((HttpMethodBase) httpMethod).getResponseContentLength());
                }
                responseString = String.valueOf(responseCode);

            }
            response.setResponseBody(httpClientCallback.process(httpMethod));
            return response;
        } finally {
            if (httpServiceTracer.getLogContext() != null) {
                httpServiceTracer.finishInvoke(String.valueOf(responseString), HttpServiceContext.class);
            }
            httpMethod.releaseConnection();
        }
    }
    
    /** 当前应用名 */
    private String     currentApp;
    private int        maxConnPerHost           = 6;

    private int        maxTotalConn             = 10;

    /** 默认等待连接建立超时，单位:毫秒*/
    private int        connectionTimeout        = 1000;

    /** 默认等待数据返回超时，单位:毫秒*/
    private int        soTimeout                = 10000;

    /** 默认请求连接池连接超时,单位:毫秒*/
    private int        connectionManagerTimeout = 1000;
    
    public void setMaxConnPerHost(int maxConnPerHost) {
        this.maxConnPerHost = maxConnPerHost;
    }

    public void setMaxTotalConn(int maxTotalConn) {
        this.maxTotalConn = maxTotalConn;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public void setConnectionManagerTimeout(int connectionManagerTimeout) {
        this.connectionManagerTimeout = connectionManagerTimeout;
    }

    public void setAppName(String appName) {
        this.currentApp = appName;
    }
}