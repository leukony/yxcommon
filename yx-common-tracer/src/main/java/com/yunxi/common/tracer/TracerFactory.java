package com.yunxi.common.tracer;

import com.yunxi.common.tracer.tracer.HttpTracer;

/**
 * Tracer工厂
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerFactory.java, v 0.1 2017年1月12日 下午4:44:43 leukony Exp $
 */
public class TracerFactory {

    private static volatile HttpTracer httpClientTracer;

    private static volatile HttpTracer httpServerTracer;

    public static HttpTracer getHttpClientTracer() {
        if (httpClientTracer == null) {
            synchronized (TracerFactory.class) {
                if (httpClientTracer == null) {
                    httpClientTracer = new HttpTracer();
                }
            }
        }
        return httpClientTracer;
    }

    public static HttpTracer getHttpServerTracer() {
        if (httpServerTracer == null) {
            synchronized (TracerFactory.class) {
                if (httpServerTracer == null) {
                    httpServerTracer = new HttpTracer();
                }
            }
        }
        return httpServerTracer;
    }
}