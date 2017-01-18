package com.yunxi.common.lang.logging;

import java.io.File;
import java.io.IOException;

/**
 * 自动创建文件目录的appender
 *
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: DailyRollingFileAppender.java, v 0.1 2016年12月26日 下午4:52:45 leukony Exp $
 */
public class DailyRollingFileAppender extends org.apache.log4j.DailyRollingFileAppender {
	
	@Override
    public synchronized void setFile(String fileName, boolean append,
    	boolean bufferedIO, int bufferSize) throws IOException {
        File logfile = new File(fileName);

        logfile.getParentFile().mkdirs();

        super.setFile(fileName, append, bufferedIO, bufferSize);
    }
}