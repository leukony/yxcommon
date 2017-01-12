package com.yunxi.common.tracer.tracer;

import com.yunxi.common.tracer.TracerThread;
import com.yunxi.common.tracer.TracerWriter;
import com.yunxi.common.tracer.context.TracerContext;

/**
 * 基于网络调用的Tracer的基类
 * <p>如：远程调用、远端缓存、消息、DB等</p>
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: NetworkTracer.java, v 0.1 2017年1月9日 下午2:55:17 leukony Exp $
 */
@SuppressWarnings("rawtypes")
public abstract class NetworkTracer<T extends TracerContext> extends Tracer {

    /** 客户端Tracer日志类型 */
    protected char                clientTracerType;
    /** 服务端Tracer日志类型 */
    protected char                serverTracerType;

    /** 异步日志打印，所有的中间件公用一个 TracerWriter来打印日志 */
    protected static TracerWriter tracerWriter = new TracerWriter(1024);
    static {
        tracerWriter.start("NetworkAppender");
    }

    /**
     * 开始网络调用
     * @return
     * @throws Throwable
     */
    protected T startInvoke() throws Throwable {
        try {
            T child = null;

            TracerContext ctx = TracerThread.get();
            if (ctx == null) {
                child = getDefaultContext();
            } else {
                child = createChildContext(ctx);
            }

            child.setStartTime(System.currentTimeMillis());
            child.setThreadName(Thread.currentThread().getName());

            TracerThread.set(child);

            return child;
        } catch (Throwable t) {
            // TODO Trace自身异常处理
            return null;
        }
    }

    /**
     * 网络调用完毕
     * @param resultCode
     * @param expectedType
     * @throws Throwable
     */
    protected void finishInvoke(String resultCode, Class<? extends TracerContext> expectedType)
                                                                                               throws Throwable {
        try {
            createClientAppenderIfNecessary();
            
            TracerContext ctx = TracerThread.get();
            if (ctx != null) {
                ctx.setResultCode(resultCode);
                ctx.setTracerType(clientTracerType);
                ctx.setFinishTime(System.currentTimeMillis());

                tracerWriter.append(ctx);

                if (ctx.getClass().equals(expectedType)) {
                    TracerThread.set(ctx.getParentContext());
                }
            }
        } catch (Throwable t) {
            // TODO Trace自身异常处理
        }
    }

    /**
     * 开始处理网络调用
     * @return
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    protected T startProcess() throws Throwable {
        try {
            TracerContext ctx = TracerThread.get();
            if (ctx == null) {
                ctx = getDefaultContext();
                TracerThread.set(ctx);
            }

            ctx.setStartTime(System.currentTimeMillis());
            ctx.setThreadName(Thread.currentThread().getName());

            return (T) ctx;
        } catch (Throwable t) {
            // TODO Trace自身异常处理
            return null;
        }
    }

    /**
     * 网络调用处理完毕
     * @param resultCode
     */
    protected void finishProcess(String resultCode) {
        try {
            createServerAppenderIfNecessary();
            
            TracerContext ctx = TracerThread.get();
            if (ctx != null) {
                ctx.setResultCode(resultCode);
                ctx.setTracerType(serverTracerType);
                ctx.setFinishTime(System.currentTimeMillis());

                tracerWriter.append(ctx);
            }
        } catch (Throwable t) {
            // TODO Trace自身异常处理
        } finally {
            TracerThread.clear();
        }
    }

    /**
     * 复制透传属性
     * @param parent
     * @param child
     */
    public void cloneTraceContext(TracerContext parent, T child) {
        // 公共属性
        child.setTraceId(parent.getTraceId());
        child.setTraceIndex(parent.nextChildTraceIndex());
        // 系统属性
        // 业务属性

        // TODO
        child.setParentContext(null);
    }

    /**
     * 创建默认的 Tracer日志上下文
     * @return Tracer日志上下文
     */
    protected abstract T getDefaultContext();

    /**
     * 创建子Tracer日志上下文
     * @param parentCtx 父Tracer日志上下文
     * @return 根据父Tracer日志上下文创建子Tracer日志上下文
     */
    protected abstract T createChildContext(TracerContext parentCtx);
    
    /**
     * 创建网络调用的Appender
     */
    protected abstract void createClientAppenderIfNecessary();
    
    /**
     * 创建处理网络调用的Appender 
     */
    protected abstract void createServerAppenderIfNecessary();
}