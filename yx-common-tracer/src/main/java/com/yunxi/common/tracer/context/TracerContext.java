package com.yunxi.common.tracer.context;

import static com.yunxi.common.tracer.TracerConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Tracer日志上下文
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerContext.java, v 0.1 2017年1月9日 下午2:49:33 leukony Exp $
 */
public abstract class TracerContext {

    private static ThreadLocal<TracerContext> THREADLOCAL = new ThreadLocal<TracerContext>();

    public static TracerContext get() {
        return THREADLOCAL.get();
    }

    public static void set(TracerContext ctx) {
        THREADLOCAL.set(ctx);
    }

    public String getTraceId() {
        return getTrace(TRACE_ID);
    }

    public void setTraceId(String traceId) {
        putTrace(TRACE_ID, traceId == null ? StringUtils.EMPTY : traceId);
    }
    
    public String getTraceIndex() {
        return getTrace(TRACE_INDEX);
    }

    public void setTraceIndex(String traceIndex) {
        putTrace(TRACE_INDEX, traceIndex == null ? StringUtils.EMPTY : traceIndex);
    }

    /** 开始时间  */
    private long          startTime;
    /** 结束时间 */
    private long          finishTime;
    /** 应用名 */
    private String        currentApp;
    /** 线程名 */
    private String        threadName;
    /** 返回结果码 */
    private String        resultCode;
    /** Tracer类型 */
    private String        tracerType;
    /** 父Trace日志上下文  */
    private TracerContext parentContext;

    /**
     * 获取耗时
     * @return
     */
    public long getUseTime() {
        if (finishTime == 0L) {
            finishTime = System.currentTimeMillis();
        }
        return finishTime - startTime;
    }
    
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
    
    public String getCurrentApp() {
        return currentApp;
    }
    
    public void setCurrentApp(String currentApp) {
        this.currentApp = currentApp;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    
    public String getTracerType() {
        return tracerType;
    }
    
    public void setTracerType(String tracerType) {
        this.tracerType = tracerType;
    }
    
    public TracerContext getParentContext() {
        return parentContext;
    }
    
    public void setParentContext(TracerContext parentContext) {
        this.parentContext = parentContext;
    }

    /** 扁平化的 Map 存放各种Trace的数据 */
    Map<String, String> traceContext = new HashMap<String, String>();

    public Map<String, String> getTraceContext() {
        return traceContext;
    }

    public void setTraceContext(Map<String, String> traceContext) {
        this.traceContext = traceContext;
    }

    public void putAllTrace(Map<String, String> traceContext) {
        this.traceContext.putAll(traceContext);
    }

    public void putTrace(String key, String value) {
        this.traceContext.put(key, value);
    }

    public String getTrace(String key) {
        return this.traceContext.get(key);
    }
    
    /**
     * 复制实例
     * @return 本实例的一个克隆
     */
    public abstract TracerContext clone();
    
    /**
     * 复制实例
     * @param to
     * @return
     */
    public TracerContext clone(TracerContext to) {
        to.startTime = this.startTime;
        to.finishTime = this.finishTime;
        to.currentApp = this.currentApp;
        to.resultCode = this.resultCode;
        to.tracerType = this.tracerType;
        to.parentContext = this.parentContext;
        to.putAllTrace(this.traceContext);
        return this;
    }
}
