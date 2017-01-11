package com.yunxi.common.tracer.tracer;

import com.yunxi.common.tracer.context.HttpServiceContext;
import com.yunxi.common.tracer.context.TracerContext;
import com.yunxi.common.tracer.util.TraceIdGenerator;

/**
 * 调用WEB服务或处理WEB请求的Tracer
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpServiceTracer.java, v 0.1 2017年1月9日 下午3:31:30 leukony Exp $
 */
public class HttpServiceTracer extends NetworkTracer<HttpServiceContext> {
    
    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#startProcess()
     */
    @Override
    protected HttpServiceContext startProcess() throws Throwable {
        try {
            return super.startProcess();
        } catch (Throwable t) {
            // TODO Trace自身异常处理
            return null;
        }
    }
    
    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#finishProcess(java.lang.String)
     */
    @Override
    protected void finishProcess(String resultCode) {
        // TODO 创建Appender
        super.finishProcess(resultCode);
    }
    
    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#getDefaultContext()
     */
    @Override
    protected HttpServiceContext getDefaultContext() {
        HttpServiceContext httpContext = new HttpServiceContext();
        httpContext.setTraceId(TraceIdGenerator.generate());
        return httpContext;
    }
    
    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#createChildContext(com.yunxi.common.tracer.context.TracerContext)
     */
    @Override
    protected HttpServiceContext createChildContext(TracerContext parentCtx) {
        HttpServiceContext httpContext = new HttpServiceContext();
        cloneTraceAttr(parentCtx, httpContext);
        return httpContext;
    }
}