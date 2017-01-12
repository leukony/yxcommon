package com.yunxi.common.tracer.appender;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.yunxi.common.tracer.constants.TracerConstants;

/**
 * 基于滚动的Tracer的日志打印
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: RollingFileAppender.java, v 0.1 2017年1月10日 下午4:35:15 leukony Exp $
 */
public abstract class RollingFileAppender implements TracerAppender {

    /** 日志刷新间隔，buffer时间超过间隔，则把缓存的日志数据刷新出去 */
    public static final long       FLUSH_INTERVAL = TimeUnit.SECONDS.toMillis(1);

    /** 默认输出缓冲大小：8KB */
    public static final int        DEFAULT_BUFFER = 8 * 1024;

    /** 日志文件名 */
    protected final String         fileName;

    /** 日志缓冲大小 */
    protected final int            bufferSize;
    
    protected long                 flushTime      = 0L;

    protected BufferedOutputStream buffer         = null;

    protected File                 file           = null;

    protected final AtomicBoolean  isRolling      = new AtomicBoolean(false);

    public RollingFileAppender(String fileName) {
        this(fileName, DEFAULT_BUFFER);
    }

    public RollingFileAppender(String fileName, int bufferSize) {
        this.fileName = fileName;
        this.bufferSize = bufferSize;
        this.initialize();
    }

    /**
     * 初始化日志文件
     */
    protected void initialize() {
        try {
            file = new File(fileName);
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    if (!parentFile.mkdirs()) {
                        // TODO Error LOG
                        return;
                    }
                }
                if (!file.createNewFile()) {
                    // TODO Error LOG
                    return;
                }
            }

            if (!file.isFile()) {
                // TODO Error LOG
                return;
            }

            if (!file.canWrite()) {
                // TODO Error LOG
                return;
            }

            OutputStream os = new FileOutputStream(file, true);
            buffer = new BufferedOutputStream(os, bufferSize);
        } catch (Throwable t) {
            // TODO Error LOG
        }
    }

    /** 
     * @see com.yunxi.common.tracer.appender.TracerAppender#flush()
     */
    @Override
    public void flush() throws IOException {
        if (buffer != null) {
            buffer.flush();
        }
    }

    /** 
     * @see com.yunxi.common.tracer.appender.TracerAppender#append(java.lang.String)
     */
    @Override
    public void append(String log) throws IOException {
        if (buffer != null) {

            waitRollOver();

            if (checkRollOver() && isRolling.compareAndSet(false, true)) {
                try {
                    rollOver();
                    long current = System.currentTimeMillis();
                    flushTime = current + FLUSH_INTERVAL;
                } finally {
                    isRolling.set(false);
                }
            } else {
                // 达到指定刷新时间，自动刷新一次
                long current = System.currentTimeMillis();
                if (current >= flushTime) {
                    buffer.flush();
                    flushTime = current + FLUSH_INTERVAL;
                }
            }

            buffer.write(log.getBytes(TracerConstants.DEFAULT_CHARSET));
        }
    }

    /**
     * 等待日志滚动完成
     */
    private void waitRollOver() {
        while (isRolling.get()) {
            try {
                Thread.sleep(1L);
            } catch (Exception e) {
                // TODO
            }
        }
    }

    /**
     * 进行日志滚动
     */
    protected abstract void rollOver();

    /**
     * 检查是否需要日志滚动
     * @return
     */
    protected abstract boolean checkRollOver();
}