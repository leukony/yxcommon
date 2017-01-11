package com.yunxi.common.tracer.tracer;

import com.yunxi.common.tracer.TracerWriter;
import com.yunxi.common.tracer.context.TracerContext;

/**
 * 基于网络调用的Tracer的基类
 * <p>如：远程调用、远端缓存、消息、DB等</p>
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: NetworkTracer.java, v 0.1 2017年1月9日 下午2:55:17 leukony Exp $
 */
public abstract class NetworkTracer<T extends TracerContext> extends Tracer {
    
    /** 异步日志打印，所有的中间件公用一个 AsyncAppender 来打印日志 */
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
            T childCtx = null;

            TracerContext ctx = TracerContext.get();
            if (ctx == null) {
                childCtx = getDefaultContext();
            } else {
                childCtx = createChildContext(ctx);
            }

            TracerContext.set(childCtx);

            childCtx.setStartTime(System.currentTimeMillis());
            childCtx.setThreadName(Thread.currentThread().getName());

            return childCtx;
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
        TracerContext ctx = TracerContext.get();
        if (ctx != null) {
            ctx.setResultCode(resultCode);
            ctx.setFinishTime(System.currentTimeMillis());
            
            // TODO 统计日志
            
            if (ctx.getClass().equals(expectedType)) {
                TracerContext.set(ctx.getParentContext());
            }
        }
    }

    /**
     * 开始处理网络调用
     * @return
     * @throws Throwable
     */
    protected T startProcess() throws Throwable {
        TracerContext ctx = TracerContext.get();
        if (ctx == null) {
            ctx = getDefaultContext();
            TracerContext.set(ctx);
        }

        ctx.setStartTime(System.currentTimeMillis());
        ctx.setThreadName(Thread.currentThread().getName());

        return (T) ctx;
    }

    /**
     * 网络调用处理完毕
     * @param resultCode
     */
    protected void finishProcess(String resultCode) {
        try {
            TracerContext ctx = TracerContext.get();
            if (ctx != null) {
                ctx.setResultCode(resultCode);
                ctx.setFinishTime(System.currentTimeMillis());

                // TODO 追加日志
                // TODO 统计日志
            }
        } catch (Throwable t) {
            // TODO Trace自身异常处理
        } finally {
            clear();
        }
    }

    /**
     * 清理Tracer日志上下文
     */
    public void clear() {
        TracerContext.set(null);
    }
    
    /**
     * 复制父Tracer日志上下文的透传属性给子Tracer中
     * @param parentContext
     * @param childContext
     */
    public void cloneTraceAttr(TracerContext parentContext, T childContext) {
        // 公共属性
        childContext.setTraceId(parentContext.getTraceId());
        // 系统属性
        // 业务属性
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
}