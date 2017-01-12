package com.yunxi.common.tracer.context;

import static com.yunxi.common.tracer.constants.TracerConstants.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;

/**
 * Tracer日志上下文
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerContext.java, v 0.1 2017年1月9日 下午2:49:33 leukony Exp $
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class TracerContext<T extends TracerContext> {

    public TracerContext() {
    }

    public TracerContext(Map<String, String> traceContext) {
        putAllTrace(traceContext);
    }

    public TracerContext(String tracerId, String tracerIndex) {
        setTraceId(tracerId);
        setTraceIndex(tracerIndex);
    }

    /**
     * 复制实例
     * @return 本实例的一个克隆
     */
    public abstract T clone();

    /**
     * 复制Tracer基本属性
     * @param to
     * @return
     */
    public T clone(T to) {
        to.setStartTime(this.startTime);
        to.setFinishTime(this.finishTime);
        to.setCurrentApp(this.currentApp);
        to.setResultCode(this.resultCode);
        to.setTracerType(this.tracerType);
        to.setParentContext(this.parentContext);
        to.setChildTraceIndex(this.childTraceIndex);
        to.putAllTrace(this.traceContext);
        return to;
    }

    /** 扁平化的 Map 存放各种Trace的数据 */
    private Map<String, String> traceContext = new HashMap<String, String>();

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

    public void putTrace(String key, String value) {
        this.traceContext.put(key, value);
    }

    public String getTrace(String key) {
        return this.traceContext.get(key);
    }

    public void putAllTrace(Map<String, String> traceContext) {
        this.traceContext.putAll(traceContext);
    }

    /** 上下文计数器 */
    private AtomicInteger childTraceIndex = new AtomicInteger(0);

    /**
     * 获取下一个子上下文的TraceIndex
     * @return
     */
    public String nextChildTraceIndex() {
        StringBuilder sb = new StringBuilder();
        sb.append(traceContext.get(TRACE_INDEX));
        sb.append(TRACE_INDEX_SEPARATOR);
        sb.append(childTraceIndex.incrementAndGet());
        return sb.toString();
    }

    /**
     * 获取上一个子上下文的TraceIndex
     * @return
     */
    public String lastChildTraceIndex() {
        StringBuilder sb = new StringBuilder();
        sb.append(traceContext.get(TRACE_INDEX));
        sb.append(TRACE_INDEX_SEPARATOR);
        sb.append(childTraceIndex.get());
        return sb.toString();
    }

    /**
      * Getter method for property <tt>childTraceIndex</tt>.
      * 
      * @return property value of childTraceIndex
      */
    public AtomicInteger getChildTraceIndex() {
        return childTraceIndex;
    }

    /**
      * Setter method for property <tt>childTraceIndex</tt>.
      * 
      * @param childTraceIndex value to be assigned to property childTraceIndex
      */
    public void setChildTraceIndex(AtomicInteger childTraceIndex) {
        this.childTraceIndex = childTraceIndex;
    }

    /** 开始时间  */
    private long          startTime;
    /** 结束时间 */
    private long          finishTime;
    /** 类型 */
    private char          tracerType;
    /** 应用名 */
    private String        currentApp;
    /** 线程名 */
    private String        threadName;
    /** 返回结果码 */
    private String        resultCode;
    /** 父Trace日志上下文  */
    private TracerContext parentContext;

    /**
     * 获取耗时
     * @return
     */
    public long getUsedTime() {
        if (finishTime == 0L) {
            finishTime = System.currentTimeMillis();
        }
        return finishTime - startTime;
    }

    /**
      * Getter method for property <tt>startTime</tt>.
      * 
      * @return property value of startTime
      */
    public long getStartTime() {
        return startTime;
    }

    /**
      * Setter method for property <tt>startTime</tt>.
      * 
      * @param startTime value to be assigned to property startTime
      */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
      * Getter method for property <tt>finishTime</tt>.
      * 
      * @return property value of finishTime
      */
    public long getFinishTime() {
        return finishTime;
    }

    /**
      * Setter method for property <tt>finishTime</tt>.
      * 
      * @param finishTime value to be assigned to property finishTime
      */
    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
    
    /**
      * Getter method for property <tt>tracerType</tt>.
      * 
      * @return property value of tracerType
      */
    public char getTracerType() {
        return tracerType;
    }
    
    /**
      * Setter method for property <tt>tracerType</tt>.
      * 
      * @param tracerType value to be assigned to property tracerType
      */
    public void setTracerType(char tracerType) {
        this.tracerType = tracerType;
    }

    /**
      * Getter method for property <tt>currentApp</tt>.
      * 
      * @return property value of currentApp
      */
    public String getCurrentApp() {
        return currentApp;
    }

    /**
      * Setter method for property <tt>currentApp</tt>.
      * 
      * @param currentApp value to be assigned to property currentApp
      */
    public void setCurrentApp(String currentApp) {
        this.currentApp = currentApp;
    }

    /**
      * Getter method for property <tt>threadName</tt>.
      * 
      * @return property value of threadName
      */
    public String getThreadName() {
        return threadName;
    }

    /**
      * Setter method for property <tt>threadName</tt>.
      * 
      * @param threadName value to be assigned to property threadName
      */
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    /**
      * Getter method for property <tt>resultCode</tt>.
      * 
      * @return property value of resultCode
      */
    public String getResultCode() {
        return resultCode;
    }

    /**
      * Setter method for property <tt>resultCode</tt>.
      * 
      * @param resultCode value to be assigned to property resultCode
      */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
      * Getter method for property <tt>parentContext</tt>.
      * 
      * @return property value of parentContext
      */
    public TracerContext getParentContext() {
        return parentContext;
    }

    /**
      * Setter method for property <tt>parentContext</tt>.
      * 
      * @param parentContext value to be assigned to property parentContext
      */
    public void setParentContext(TracerContext parentContext) {
        this.parentContext = parentContext;
    }
}