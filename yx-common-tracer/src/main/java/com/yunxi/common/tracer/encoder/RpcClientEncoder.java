package com.yunxi.common.tracer.encoder;

import java.io.IOException;

import com.yunxi.common.tracer.appender.TracerAppender;
import com.yunxi.common.tracer.context.RpcContext;

/**
 * Rpc客户端服务格式编码转换
 * <p>非线程安全的</p>
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: RpcClientEncoder.java, v 0.1 2017年2月28日 下午5:02:53 leukony Exp $
 */
public class RpcClientEncoder implements TracerEncoder<RpcContext> {

    /** 
     * @see com.yunxi.common.tracer.encoder.TracerEncoder#encode(com.yunxi.common.tracer.context.TracerContext, com.yunxi.common.tracer.appender.TracerAppender)
     */
    @Override
    public void encode(RpcContext ctx, TracerAppender appender) throws IOException {
    }
}