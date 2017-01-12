package com.yunxi.common.rpc.http;

import java.io.IOException;

import org.apache.commons.httpclient.HttpMethod;

/**
 * HttpClient模板回调接口
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpClientCallback.java, v 0.1 2017年1月12日 下午7:00:14 leukony Exp $
 */
public interface HttpClientCallback<T> {

    /**
     * 处理返回结果
     * @param httpMethod
     * @return
     * @throws IOException
     */
    T process(HttpMethod httpMethod) throws IOException;
}