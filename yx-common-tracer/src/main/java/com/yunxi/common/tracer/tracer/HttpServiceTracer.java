package com.yunxi.common.tracer.tracer;

import com.yunxi.common.tracer.appender.TimedRollingFileAppender;
import com.yunxi.common.tracer.appender.TracerAppender;
import com.yunxi.common.tracer.constants.TracerConstants;
import com.yunxi.common.tracer.constants.TracerLogger;
import com.yunxi.common.tracer.constants.TracerType;
import com.yunxi.common.tracer.context.HttpServiceContext;
import com.yunxi.common.tracer.context.TracerContext;
import com.yunxi.common.tracer.encoder.HttpServiceEncoder;
import com.yunxi.common.tracer.util.TraceIdGenerator;

/**
 * 调用WEB服务或处理WEB请求的Tracer
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpServiceTracer.java, v 0.1 2017年1月9日 下午3:31:30 leukony Exp $
 */
public class HttpServiceTracer extends NetworkTracer<HttpServiceContext> {

    private volatile TracerAppender httpClientAppender;
    private volatile TracerAppender httpServerAppender;

    public HttpServiceTracer() {
        clientTracerType = TracerType.TYPE_HTTP_CLIENT.getType();
        serverTracerType = TracerType.TYPE_HTTP_SERVER.getType();
    }

    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#getDefaultContext()
     */
    @Override
    protected HttpServiceContext getDefaultContext() {
        HttpServiceContext httpServiceContext = new HttpServiceContext();
        httpServiceContext.setTraceId(TraceIdGenerator.generate());
        httpServiceContext.setTraceIndex(TracerConstants.TRACE_INDEX_ROOT);
        return httpServiceContext;
    }

    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#createChildContext(com.yunxi.common.tracer.context.TracerContext)
     */
    @Override
    @SuppressWarnings("rawtypes")
    protected HttpServiceContext createChildContext(TracerContext parentCtx) {
        HttpServiceContext httpServiceContext = new HttpServiceContext();
        cloneTraceContext(parentCtx, httpServiceContext);
        return httpServiceContext;
    }

    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#createClientAppenderIfNecessary()
     */
    @Override
    protected void createClientAppenderIfNecessary() {
        if (httpClientAppender == null) {
            synchronized (this) {
                if (httpClientAppender == null) {
                    TracerLogger logger = TracerLogger.HTTP_CLIENT_DIGEST;
                    httpClientAppender = new TimedRollingFileAppender(logger.getFileName(),
                        logger.getPattern(), logger.getReserve());
                    tracerWriter.addAppender(clientTracerType, httpClientAppender,
                        new HttpServiceEncoder());
                }
            }
        }
    }

    /** 
     * @see com.yunxi.common.tracer.tracer.NetworkTracer#createServerAppenderIfNecessary()
     */
    @Override
    protected void createServerAppenderIfNecessary() {
        if (httpServerAppender == null) {
            synchronized (this) {
                if (httpServerAppender == null) {
                    TracerLogger logger = TracerLogger.HTTP_SERVER_DIGEST;
                    httpServerAppender = new TimedRollingFileAppender(logger.getFileName(),
                        logger.getPattern(), logger.getReserve());
                    tracerWriter.addAppender(serverTracerType, httpServerAppender,
                        new HttpServiceEncoder());
                }
            }
        }
    }
}