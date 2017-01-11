package com.yunxi.common.tracer.util;

import static com.yunxi.common.tracer.TracerConstants.*;

import java.lang.management.ManagementFactory;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yunxi.common.tracer.context.TracerContext;

/**
 * Tracer工具类
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerUtils.java, v 0.1 2017年1月9日 下午2:40:13 leukony Exp $
 */
public class TracerUtils {
    
    /**
     * 克隆Tracer上下文
     * @return
     */
    public static TracerContext cloneContext() {
        TracerContext tracerContext = TracerContext.get();
        return tracerContext == null ? null : tracerContext.clone();
    }

    /**
     * 获取PID
     * <p>适用于JDK6，JDK7，JDK8，JDK9提供了更简便的实现</p>
     * @return
     */
    public static String getPID() {
        String processName = ManagementFactory.getRuntimeMXBean().getName();

        if (StringUtils.isBlank(processName)) {
            return StringUtils.EMPTY;
        }

        String[] processSplitName = processName.split("@");

        if (processSplitName.length == 0) {
            return StringUtils.EMPTY;
        }

        String pid = processSplitName[0];

        if (StringUtils.isBlank(pid)) {
            return StringUtils.EMPTY;
        }

        return pid;
    }

    /**
     * 将map转成string, 如{"k1":"v1", "k2":"v2"} => k1=v1&k2=v2&
     */
    public static String mapToString(Map<String, String> map) {
        StringBuffer sb = new StringBuffer(DEFAULT_BUFFER_SIZE);

        if (map == null) {
            sb.append(StringUtils.EMPTY);
        } else {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = escape(entry.getKey());
                String val = escape(entry.getValue());
                sb.append(key).append(EQUAL_SEPARATOR).append(val).append(AND_SEPARATOR);
            }
        }

        return sb.toString();
    }

    /**
     * 替换str中的"&"，"=" 和 "%"
     */
    private static String escape(String str) {
        str = escape(str, PERCENT, PERCENT_ESCAPE);
        str = escape(str, AND_SEPARATOR, AND_SEPARATOR_ESCAPE);
        str = escape(str, EQUAL_SEPARATOR, EQUAL_SEPARATOR_ESCAPE);
        return str;
    }

    /**
     * 将str中的oldStr替换为newStr
     */
    private static String escape(String str, String oldStr, String newStr) {
        return str == null ? StringUtils.EMPTY : str.replace(oldStr, newStr);
    }

}
