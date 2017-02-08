package com.yunxi.common.tracer;

import com.yunxi.common.tracer.tracer.HttpServiceTracer;

/**
 * Tracer工厂
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerFactory.java, v 0.1 2017年1月12日 下午4:44:43 leukony Exp $
 */
public class TracerFactory {

    private static volatile HttpServiceTracer httpClientTracer;

    private static volatile HttpServiceTracer httpServerTracer;

    public static HttpServiceTracer getHttpClientTracer() {
        if (httpClientTracer == null) {
            synchronized (TracerFactory.class) {
                if (httpClientTracer == null) {
                    httpClientTracer = new HttpServiceTracer();
                }
            }
        }
        return httpClientTracer;
    }

    public static HttpServiceTracer getHttpServerTracer() {
        if (httpServerTracer == null) {
            synchronized (TracerFactory.class) {
                if (httpServerTracer == null) {
                    httpServerTracer = new HttpServiceTracer();
                }
            }
        }
        return httpServerTracer;
    }
}