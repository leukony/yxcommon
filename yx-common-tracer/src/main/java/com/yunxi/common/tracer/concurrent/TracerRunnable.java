package com.yunxi.common.tracer.concurrent;

import com.yunxi.common.tracer.context.TracerContext;
import com.yunxi.common.tracer.util.TracerUtils;

/**
 * Tracer多线程支持：Runnable实现 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerRunnable.java, v 0.1 2017年1月11日 下午7:05:01 leukony Exp $
 */
public abstract class TracerRunnable implements Runnable {

    private TracerContext tracerContext = TracerUtils.cloneContext();

    public void run() {
        TracerContext.set(tracerContext);
        try {
            doRun();
        } finally {
            TracerContext.set(null);
        }
    }

    public abstract void doRun();
}