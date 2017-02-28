package com.yunxi.common.tracer.tracer;

import java.util.Map;

import com.yunxi.common.tracer.appender.TracerAppender;
import com.yunxi.common.tracer.constants.TracerType;
import com.yunxi.common.tracer.context.RpcContext;
import com.yunxi.common.tracer.context.TracerContext;

/**
 * 调用远程服务或处理远程请求的Tracer
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: RpcTracer.java, v 0.1 2017年2月28日 下午4:56:36 leukony Exp $
 */
public class RpcTracer extends NetworkTracer<RpcContext> {
    
    private volatile TracerAppender rpcClientAppender;
    private volatile TracerAppender rpcServerAppender;

    public RpcTracer() {
        clientTracerType = TracerType.RPC_CLIENT.getType();
        serverTracerType = TracerType.RPC_SERVER.getType();
    }

    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#getDefaultContext()
     */
    @Override
    protected RpcContext getDefaultContext() {
        return null;
    }

    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#createChildContext(com.yunxi.common.tracer.context.TracerContext)
     */
    @Override
    @SuppressWarnings("rawtypes")
    protected RpcContext createChildContext(TracerContext parentCtx) {
        return null;
    }

    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#setContext(java.util.Map)
     */
    @Override
    protected RpcContext setContext(Map<String, String> tracerContext) {
        return null;
    }

    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#createClientAppenderIfNecessary()
     */
    @Override
    protected void createClientAppenderIfNecessary() {
    }

    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#createServerAppenderIfNecessary()
     */
    @Override
    protected void createServerAppenderIfNecessary() {
    }
}