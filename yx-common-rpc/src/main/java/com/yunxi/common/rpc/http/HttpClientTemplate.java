package com.yunxi.common.rpc.http;

import static com.yunxi.common.tracer.constants.TracerConstants.RPC_ID;
import static com.yunxi.common.tracer.constants.TracerConstants.TRACE_ID;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.yunxi.common.tracer.TracerFactory;
import com.yunxi.common.tracer.context.HttpContext;
import com.yunxi.common.tracer.tracer.HttpTracer;

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
 * @version $Id: HttpClientTemplate.java, v 0.1 2017年2月22日 上午10:38:08 leukony Exp $
 */
public class HttpClientTemplate {

    /** 应用名 */
    private String     appName;
    /** HttpClient */
    private HttpClient httpClient;

    /**
     * 以Get方式执行http请求
     * @param url
     * @return
     * @throws IOException
     * @throws HttpException
     */
    public HttpClientResponse<String> executeGet(String url) throws IOException, HttpException {
        return executeGet(url, new HttpClientCallback<String>() {

            @Override
            protected String doConvert(HttpMethod httpMethod) throws IOException {
                return httpMethod.getResponseBodyAsString();
            }

        });
    }

    /**
     * 以Get方式执行http请求
     * @param url
     * @param callback
     * @return
     * @throws IOException
     * @throws HttpException
     */
    public <T> HttpClientResponse<T> executeGet(String url, HttpClientCallback<T> callback)
                                                                                           throws IOException,
                                                                                           HttpException {
        return execute(new GetMethod(url), callback);
    }

    /**
     * 以Post方式执行http请求
     * @param url
     * @param headParams
     * @param reqParams
     * @return
     * @throws IOException
     * @throws HttpException
     */
    public HttpClientResponse<String> executePost(String url, NameValuePair[] headParams,
                                                  NameValuePair[] reqParams) throws IOException,
                                                                            HttpException {
        return executePost(url, headParams, reqParams, new HttpClientCallback<String>() {

            @Override
            protected String doConvert(HttpMethod httpMethod) throws IOException {
                return httpMethod.getResponseBodyAsString();
            }

        });
    }

    /**
     * 以Post方式执行http请求
     * @param url
     * @param headParams
     * @param reqParams
     * @param callback
     * @return
     * @throws IOException
     * @throws HttpException
     */
    public <T> HttpClientResponse<T> executePost(String url, NameValuePair[] headParams,
                                                 NameValuePair[] reqParams,
                                                 HttpClientCallback<T> callback)
                                                                                throws IOException,
                                                                                HttpException {
        PostMethod postMethod = new PostMethod(url);

        if (headParams != null && headParams.length > 0) {
            for (NameValuePair headerParam : headParams) {
                if (headerParam != null) {
                    postMethod.addRequestHeader(headerParam.getName(), headerParam.getValue());
                }
            }
        }

        if (reqParams != null && reqParams.length > 0) {
            for (NameValuePair reqParam : reqParams) {
                if (reqParam != null) {
                    postMethod.addParameter(reqParam.getName(), reqParam.getValue());
                }
            }
        }

        return execute(postMethod, callback);
    }

    /**
     * 使用HttpClientCallback处理HttpClient的响应
     * @param httpMethod
     * @param callback
     * @return
     * @throws IOException
     * @throws HttpException
     */
    public <T> HttpClientResponse<T> execute(HttpMethod httpMethod, HttpClientCallback<T> callback)
                                                                                                   throws IOException,
                                                                                                   HttpException {
        String resultCode = "";
        HttpTracer httpTracer = null;

        try {
            // 1、从工厂获取HttpTracer
            httpTracer = TracerFactory.getHttpClientTracer();

            // 2、开始Http请求,调用startInvoke
            HttpContext httpContext = httpTracer.startInvoke();

            // 3、将上下文中Tracer参数设置到请求头
            if (httpContext != null) {
                httpMethod.setRequestHeader(TRACE_ID, httpContext.getTraceId());
                httpMethod.setRequestHeader(RPC_ID, httpContext.getRpcId());

                httpContext.setUrl(httpMethod.getURI().getURI());
                httpContext.setMethod(httpMethod.getName());
                httpContext.setCurrentApp(appName);

                if (httpMethod instanceof EntityEnclosingMethod) {
                    RequestEntity requestEntity = ((EntityEnclosingMethod) httpMethod)
                        .getRequestEntity();
                    if (requestEntity != null) {
                        httpContext.setRequestSize(requestEntity.getContentLength());
                    }
                }
            }

            // 4、开始Http请求,调用executeMethod
            int httpCode = httpClient.executeMethod(httpMethod);

            // 5、将Http请求结果设置到上下文中
            if (httpContext != null) {
                resultCode = String.valueOf(httpCode);
                if (httpMethod instanceof HttpMethodBase) {
                    HttpMethodBase httpMethodBase = (HttpMethodBase) httpMethod;
                    httpContext.setResponseSize(httpMethodBase.getResponseContentLength());
                }
            }

            // 6、调用CallBack处理Http请求返回结果
            return callback.process(httpMethod);
        } finally {
            // 7、结束Http请求调用，打印Trace日志
            if (httpTracer != null) {
                httpTracer.finishInvoke(resultCode, HttpContext.class);
            }

            // 8、释放Http请求链接
            httpMethod.releaseConnection();
        }
    }

    /**
      * Setter method for property <tt>appName</tt>.
      * 
      * @param appName value to be assigned to property appName
      */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /** HttpClient代理 */
    private HttpClientProxy  httpClientProxy;
    /** HttpClient参数 */
    private HttpClientParams httpClientParams;

    /**
     * 初始化HttpClient
     * @param params
     */
    public void initialize() {
        HttpConnectionManagerParams httpConnectionManagerParams = new HttpConnectionManagerParams();
        HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        httpConnectionManagerParams.setDefaultMaxConnectionsPerHost(httpClientParams
            .getMaxConnPerHost());
        httpConnectionManagerParams.setConnectionTimeout(httpClientParams.getConnectionTimeout());
        httpConnectionManagerParams.setMaxTotalConnections(httpClientParams.getMaxTotalConn());
        httpConnectionManagerParams.setSoTimeout(httpClientParams.getSoTimeout());
        httpConnectionManager.setParams(httpConnectionManagerParams);
        httpClient = new HttpClient(httpConnectionManager);
        httpClient.getParams().setConnectionManagerTimeout(
            httpClientParams.getConnectionManagerTimeout());
        httpClient.getParams().setContentCharset(httpClientParams.getContentCharset());
        if (httpClientProxy != null) {
            httpClient.getHostConfiguration().setProxy(httpClientProxy.getProxyHost(),
                httpClientProxy.getProxyPort());
            if (httpClientProxy.getProxyUserName() != null) {
                UsernamePasswordCredentials credential = new UsernamePasswordCredentials(
                    httpClientProxy.getProxyUserName(), httpClientProxy.getProxyUserPassword());
                httpClient.getState().setProxyCredentials(AuthScope.ANY, credential);
            }
        }
    }

    /**
      * Setter method for property <tt>httpClientProxy</tt>.
      * 
      * @param httpClientProxy value to be assigned to property httpClientProxy
      */
    public void setHttpClientProxy(HttpClientProxy httpClientProxy) {
        this.httpClientProxy = httpClientProxy;
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