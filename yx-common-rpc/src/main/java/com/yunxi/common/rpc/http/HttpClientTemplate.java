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
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

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

    private String           appName;
    private HttpClient       httpClient;
    private HttpClientParams httpClientParams;

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
    public String executeGet(final String url, final HttpState state) throws IOException,
                                                                     HttpException {
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
    public String executePost(final String url, final NameValuePair[] params,
                              final NameValuePair[] headerParams) throws IOException, HttpException {
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
    public String executePost(final String url, final NameValuePair[] params,
                              final NameValuePair[] headerParams, final HttpState httpState)
                                                                                            throws IOException,
                                                                                            HttpException {
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
    public String executeWithState(final HttpMethod httpMethod, final HttpState state)
                                                                                      throws IOException,
                                                                                      HttpException {
        HttpClientResponse resp = execute(httpMethod, state, new HttpClientCallback<String>() {
            public String process(HttpMethod method) throws IOException {
                return method.getResponseBodyAsString();
            }
        });

        return (String) resp.getBody();
    }

    /**
     * 使用HttpClientCallback处理HttpClient的响应
     * @param httpMethod
     * @param httpClientCallback
     * @return HttpClientResponse
     * @throws IOException
     * @throws HttpException
     */
    public HttpClientResponse execute(final HttpMethod httpMethod,
                                      final HttpClientCallback<?> httpClientCallback)
                                                                                     throws IOException,
                                                                                     HttpException {
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
    public HttpClientResponse execute(final HttpMethod httpMethod, final HttpState httpState,
                                      final HttpClientCallback<?> httpClientCallback)
                                                                                     throws IOException,
                                                                                     HttpException {
        String resultCode = "";
        HttpServiceTracer httpServiceTracer = TracerFactory.getHttpClientTracer();
        HttpServiceContext httpServiceContext = httpServiceTracer.startInvoke();
        try {
            if (httpServiceContext != null) {
                httpMethod.setRequestHeader(TracerConstants.TRACE_ID,
                    httpServiceContext.getTraceId());
                httpMethod.setRequestHeader(TracerConstants.TRACE_INDEX,
                    httpServiceContext.getTraceIndex());

                httpServiceContext.setCurrentApp(appName);
                httpServiceContext.setMethod(httpMethod.getName());
                httpServiceContext.setUrl(getHttpClientUrl(httpMethod));

                if (httpMethod instanceof EntityEnclosingMethod) {
                    RequestEntity requestEntity = ((EntityEnclosingMethod) httpMethod)
                        .getRequestEntity();
                    if (requestEntity != null) {
                        httpServiceContext.setRequestSize(requestEntity.getContentLength());
                    }
                }
            }

            int responseCode = httpClient.executeMethod(null, httpMethod, httpState);

            if (httpServiceContext != null) {
                if (httpMethod instanceof HttpMethodBase) {
                    httpServiceContext.setResponseSize(((HttpMethodBase) httpMethod)
                        .getResponseContentLength());
                }
                resultCode = String.valueOf(responseCode);
            }

            HttpClientResponse response = new HttpClientResponse();
            response.setCode(responseCode);
            response.setBody(httpClientCallback.process(httpMethod));
            return response;
        } finally {
            httpServiceTracer.finishInvoke(resultCode, HttpServiceContext.class);
            httpMethod.releaseConnection();
        }
    }

    /**
     * 获取请求的URL
     * @param httpMethod
     * @return
     * @throws URIException
     */
    private String getHttpClientUrl(HttpMethod httpMethod) throws URIException {
        URI uri = httpMethod.getURI();

        StringBuilder sb = new StringBuilder();
        sb.append(uri.getScheme());
        sb.append("://");
        sb.append(uri.getHost());
        sb.append(uri.getPort() != -1 ? ":" + uri.getPort() : "");
        sb.append(uri.getPath());

        return sb.toString();
    }

    /**
     * 初始化
     */
    public void initialize() {
        HttpConnectionManagerParams httpClientParam = new HttpConnectionManagerParams();
        httpClientParam.setDefaultMaxConnectionsPerHost(httpClientParams.getMaxConnPerHost());
        httpClientParam.setConnectionTimeout(httpClientParams.getConnectionTimeout());
        httpClientParam.setMaxTotalConnections(httpClientParams.getMaxTotalConn());
        httpClientParam.setSoTimeout(httpClientParams.getSoTimeout());

        HttpConnectionManager httpClientManager = new MultiThreadedHttpConnectionManager();
        httpClientManager.setParams(httpClientParam);

        httpClient = new HttpClient(httpClientManager);
        httpClient.getParams().setConnectionManagerTimeout(
            httpClientParams.getConnectionManagerTimeout());
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
      * Setter method for property <tt>httpClient</tt>.
      * 
      * @param httpClient value to be assigned to property httpClient
      */
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
      * Setter method for property <tt>httpClientParams</tt>.
      * 
      * @param httpClientParams value to be assigned to property httpClientParams
      */
    public void setHttpClientParams(HttpClientParams httpClientParams) {
        this.httpClientParams = httpClientParams;
    }
}