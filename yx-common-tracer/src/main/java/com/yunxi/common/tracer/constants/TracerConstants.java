package com.yunxi.common.tracer.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Tracer常量
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerConstants.java, v 0.1 2017年1月10日 上午10:45:55 leukony Exp $
 */
public class TracerConstants {

    /** TraceId 放在透传上下文中的 key */
    public static final String  TRACE_ID                 = "traceId";
    /** RpcId 放在透传上下文中的 key */
    public static final String  RPC_ID                   = "rpcId";
    /** RpcId 根 */
    public static final String  RPC_ID_ROOT              = "0";
    /** RpcId 分隔符 */
    public static final String  RPC_ID_SEPARATOR         = ".";
    /** Trace日志默认编码 */
    public static final Charset DEFAULT_CHARSET          = StandardCharsets.UTF_8;

    /** Trace上下文转义使用到的常量 */
    public static final int     DEFAULT_BUFFER_SIZE      = 256;
    public static final char    DEFAULT_SEPARATOR        = ',';
    public static final String  DEFAULT_SEPARATOR_ESCAPE = "%2C";
    public static final String  AND_SEPARATOR            = "&";
    public static final String  AND_SEPARATOR_ESCAPE     = "%26";
    public static final String  EQUAL_SEPARATOR          = "=";
    public static final String  EQUAL_SEPARATOR_ESCAPE   = "%3D";
    public static final String  PERCENT                  = "%";
    public static final String  PERCENT_ESCAPE           = "%25";
    public static final String  NEWLINE                  = "\r\n";
}