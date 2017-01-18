package com.yunxi.common.tracer.tracer;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.yunxi.common.tracer.daemon.TracerClear;
import com.yunxi.common.tracer.util.TracerUtils;

/**
 * Tracer基类
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: Tracer.java, v 0.1 2017年1月9日 下午2:30:54 leukony Exp $
 */
public abstract class Tracer {

    private static final String TRACE_LOGGINGROOT_KEY = "tracer.loggerroot";
    private static final String TRACE_APPENDPID_KEY   = "tracer.loggerroot.appendpid";

    private static final String TRACR_LOGGING_ROOT;

    static {
        // 获取Trace日志根目录，默认为"当前用户根目录/logs/tracelog"
        String loggingRoot = System.getProperty(TRACE_LOGGINGROOT_KEY);
        if (StringUtils.isBlank(loggingRoot)) {
            loggingRoot = System.getProperty("user.home") + File.separator + "logs";
        }
        loggingRoot = loggingRoot + File.separator + "trace";

        // 获取Trace日志根目录是否需要追加PID，解决单机多应用部署日志目录冲突
        String appendPid = System.getProperty(TRACE_APPENDPID_KEY);
        if (StringUtils.equalsIgnoreCase(appendPid, Boolean.TRUE.toString())) {
            loggingRoot = loggingRoot + File.separator + TracerUtils.getPID();
        }

        // 设置Trace日志根目录
        TRACR_LOGGING_ROOT = loggingRoot;

        // 启动Trace日志清理守护线程
        try {
            TracerClear.start();
        } catch (Exception e) {
            // TODO Error LOG
        }
    }
    
    /**
     * 获取日志根目录
     * @return
     */
    public String getLoggingRoot() {
        return TRACR_LOGGING_ROOT;
    }
}