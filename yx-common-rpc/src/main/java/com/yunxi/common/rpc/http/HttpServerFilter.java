package com.yunxi.common.rpc.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.yunxi.common.tracer.TracerFactory;
import com.yunxi.common.tracer.constants.TracerConstants;
import com.yunxi.common.tracer.context.HttpServiceContext;
import com.yunxi.common.tracer.tracer.HttpServiceTracer;

/**
 * Http Server Tracer Filter
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: HttpServerFilter.java, v 0.1 2017年1月12日 下午5:11:21 leukony Exp $
 */
public class HttpServerFilter implements Filter {

    /** 应用名 */
    private String appName;

    /** 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1、获取WEB请求中的Tracer参数
        String traceId = request.getHeader(TracerConstants.TRACE_ID);
        String rpcId = request.getHeader(TracerConstants.RPC_ID);

        Map<String, String> tracerContext = null;
        if (traceId != null && traceId.length() > 0 && rpcId != null && rpcId.length() > 0) {
            tracerContext = new HashMap<String, String>();
            tracerContext.put(TracerConstants.TRACE_ID, traceId);
            tracerContext.put(TracerConstants.RPC_ID, rpcId);
        }

        // 2、从工厂中获取HttpServerTracer
        HttpServiceTracer httpServiceTracer = TracerFactory.getHttpServerTracer();

        // 3、将请求中的Tracer参数设置到上下文中
        if (tracerContext != null) {
            httpServiceTracer.setContext(tracerContext);
        }

        // 4. 开始处理WEB请求，调用startProcess
        HttpServiceContext httpServiceContext = httpServiceTracer.startProcess();

        // 5. 获取WEB请求参数并设置到Tracer上下文中
        HttpServletRequest httpReq = request;
        httpServiceContext.setUrl(httpReq.getRequestURL().toString());
        httpServiceContext.setRequestSize(httpReq.getContentLength());
        httpServiceContext.setMethod(httpReq.getMethod());
        httpServiceContext.setCurrentApp(appName);

        EnhanceResponseWrapper wrapper = new EnhanceResponseWrapper(response);

        try {
            chain.doFilter(request, wrapper);
        } finally {
            httpServiceContext.setResponseSize(wrapper.length);
            httpServiceTracer.finishProcess(String.valueOf(wrapper.status));
        }
    }

    /** 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        appName = filterConfig.getInitParameter("appName");
    }

    /** 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

    class EnhanceResponseWrapper extends HttpServletResponseWrapper {

        int length = 0;
        int status = 200;

        public EnhanceResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        /**
         * @see javax.servlet.http.HttpServletResponseWrapper#setStatus(int)
         */
        @Override
        public void setStatus(int status) {
            this.status = status;
            super.setStatus(status);
        }

        /**
         * @see javax.servlet.ServletResponseWrapper#setContentLength(int)
         */
        @Override
        public void setContentLength(int contentLength) {
            this.length = contentLength;
            super.setContentLength(contentLength);
        }
    }
}