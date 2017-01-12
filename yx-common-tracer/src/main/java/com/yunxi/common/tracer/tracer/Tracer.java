package com.yunxi.common.tracer.tracer;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.yunxi.common.tracer.util.TracerUtils;

/**
 * Tracer基类
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: Tracer.java, v 0.1 2017年1月9日 下午2:30:54 leukony Exp $
 */
public abstract class Tracer {
    
    public static final String TRACR_LOG_PATH; 
    
    static {
        String appendPid = System.getProperty("traceAppendPid");
        String loggingRoot = System.getProperty("traceLoggingRoot");
        
        if (StringUtils.isBlank(loggingRoot)) {
            loggingRoot = System.getProperty("user.home") + File.separator + "logs";
        }
        
        loggingRoot = loggingRoot + File.separator + "tracelog";
        
        if (StringUtils.equalsIgnoreCase(appendPid, Boolean.TRUE.toString())) {
            loggingRoot = loggingRoot + File.separator + TracerUtils.getPID();
        }

        TRACR_LOG_PATH = loggingRoot;
        
        // 启动删除日志守护线程
    }
}