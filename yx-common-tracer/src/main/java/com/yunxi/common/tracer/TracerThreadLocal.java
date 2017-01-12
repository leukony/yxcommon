package com.yunxi.common.tracer;

import com.yunxi.common.tracer.context.TracerContext;

/**
 * Tracer线程上下文
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerThreadLocal.java, v 0.1 2017年1月12日 下午1:24:18 leukony Exp $
 */
@SuppressWarnings("rawtypes")
public class TracerThreadLocal {

    private static ThreadLocal<TracerContext> THREADLOCAL = new ThreadLocal<TracerContext>();

    public static TracerContext get() {
        return THREADLOCAL.get();
    }

    public static void set(TracerContext ctx) {
        THREADLOCAL.set(ctx);
    }

    public static void clear() {
        THREADLOCAL.set(null);
    }
}