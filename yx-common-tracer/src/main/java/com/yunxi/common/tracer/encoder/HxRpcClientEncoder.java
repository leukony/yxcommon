package com.yunxi.common.tracer.encoder;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunxi.common.lang.enums.CommonYN;
import com.yunxi.common.lang.util.DateUtils;
import com.yunxi.common.tracer.appender.TracerAppender;
import com.yunxi.common.tracer.constants.TracerType;
import com.yunxi.common.tracer.context.RpcContext;
import com.yunxi.common.tracer.util.TracerBuilder;

/**
 * Rpc客户端服务格式编码转换
 * <p>非线程安全的</p>
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: RpcClientEncoder.java, v 0.1 2017年2月28日 下午5:02:53 leukony Exp $
 */
public class HxRpcClientEncoder implements TracerEncoder<RpcContext> {

    /** 应用统计日志 */
    protected static final Logger LOGGER = LoggerFactory.getLogger("APP-STATS-LOGGER");
    
    private TracerBuilder tb = new TracerBuilder();
    
    private static final String PREFIX_z = "[";    
    private static final String SUFFIX_z = "]";
    private static final String PREFIX_k = "{";    
    private static final String SUFFIX_k = "}";
    private static final String SPACE  = " ";
    private static final String STATS  = "STATS";

    /** 
     * @see com.yunxi.common.tracer.encoder.TracerEncoder#encode(com.yunxi.common.tracer.context.TracerContext, com.yunxi.common.tracer.appender.TracerAppender)
     */
    @Override
    public void encode(RpcContext ctx, TracerAppender appender) throws IOException {
        tb.reset();
        
        tb.appendRaw(PREFIX_z).appendRaw(DateUtils.format(ctx.getFinishTime(), DateUtils.MILLS_FORMAT_PATTERN)).appendRaw(SUFFIX_z).appendRaw(SPACE)
          .appendRaw(PREFIX_z).appendRaw(STATS).appendRaw(SUFFIX_z).appendRaw(SPACE)
          .appendRaw(PREFIX_z).appendRaw(ctx.getTraceId()).appendRaw(SUFFIX_z).appendRaw(SPACE)
          .appendRaw(PREFIX_z).appendRaw(ctx.getCurrentApp()).appendRaw(SUFFIX_z).appendRaw(SPACE)
          .appendRaw(PREFIX_z).appendRaw(TracerType.getTracerType(ctx.getTracerType()).name()).appendRaw(SUFFIX_z).appendRaw(SPACE)
          .appendRaw(PREFIX_z)
          .appendRaw(PREFIX_k)
          .appendRaw("\"serviceName\":\"" + ctx.getServiceName() + "\",")
          .appendRaw("\"methodName\":\"" + ctx.getMethodName() + "\",")
          .appendRaw("\"protocol\":\"" + ctx.getProtocol() + "\",")
          .appendRaw("\"rpcType\":\"" + ctx.getRpcType() + "\",")
          .appendRaw("\"callApp\":\"" + ctx.getCallApp() + "\",")
          .appendRaw("\"callIP\":\"" + ctx.getCallIP() + "\",")
          .appendRaw("\"targetIP\":\"" + ctx.getTargetIP() + "\",")
          .appendRaw("\"success\":\"" + CommonYN.get(ctx.isSuccess()).name() + "\",")
          .appendRaw("\"resultCode\":\"" + ctx.getResultCode() + "\",")
          .appendRaw("\"used\":" + ctx.getUsedTime() + ",")
          .appendEscapeRaw("\"thread\":\"" + ctx.getThreadName() + "\"")
          .appendRaw(SUFFIX_k)
          .appendRaw(SUFFIX_z);
        
        LOGGER.info(tb.toString());
    }
}