package com.yunxi.common.tracer;

/**
 * Tracer常量
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerConstants.java, v 0.1 2017年1月10日 上午10:45:55 leukony Exp $
 */
public class TracerConstants {

    /** TraceId 放在透传上下文中的 key */
    public static final String TRACE_ID                 = "traceId";
    /** TraceIndex 放在透传上下文中的 key */
    public static final String TRACE_INDEX              = "traceIndex";

    /** Trace上下文转义使用到的常量 */
    public static final int    DEFAULT_BUFFER_SIZE      = 256;
    public static final char   DEFAULT_SEPARATOR        = ',';
    public static final String DEFAULT_SEPARATOR_ESCAPE = "%2C";
    public static final String AND_SEPARATOR            = "&";
    public static final String AND_SEPARATOR_ESCAPE     = "%26";
    public static final String EQUAL_SEPARATOR          = "=";
    public static final String EQUAL_SEPARATOR_ESCAPE   = "%3D";
    public static final String PERCENT                  = "%";
    public static final String PERCENT_ESCAPE           = "%25";
    public static final String NEWLINE                  = "\r\n";
}