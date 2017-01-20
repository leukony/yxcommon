package com.yunxi.common.tracer.appender;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.yunxi.common.lang.util.DateUtils;
import com.yunxi.common.tracer.daemon.TracerClear;

/**
 * 基于时间滚动的Tracer的日志打印
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TimedRollingFileAppender.java, v 0.1 2017年1月10日 下午3:08:41 leukony Exp $
 */
public class TimedRollingFileAppender extends RollingFileAppender {

    public static final int    TOP_OF_TROUBLE          = -1;
    public static final int    TOP_OF_SECONDS          = 0;
    public static final int    TOP_OF_MINUTE           = 1;
    public static final int    TOP_OF_HOUR             = 2;
    public static final int    HALF_DAY                = 3;
    public static final int    TOP_OF_DAY              = 4;
    public static final int    TOP_OF_WEEK             = 5;
    public static final int    TOP_OF_MONTH            = 6;

    public static final String DAILY_ROLLING_PATTERN   = "'.'yyyy-MM-dd";
    public static final String HOURLY_ROLLING_PATTERN  = "'.'yyyy-MM-dd_HH";
    public static final String DEFAULT_ROLLING_PATTERN = DAILY_ROLLING_PATTERN;
    public static final int    DEFAULT_LOG_RESERVE_DAY = 7;
    
    /** 日志的保留天数 */
    private int reserve;
    /** 备份的文件的后缀模式 */
    private String pattern;


    /** 用于计算下次RollOver的时间 */
    private RollingCalendar calendar;
    /** 用于格式化文件名用 */
    private SimpleDateFormat format;
    
    
    private Date currentDate = DateUtils.currentDate();
    /** 下次RollOver发生的时间 */
    private long nextRollOver = System.currentTimeMillis() - 1;
    /** 下次RollOver生成的文件 */
    private String nextFileName;

    public TimedRollingFileAppender(String file) {
        this(file, DEFAULT_BUFFER, DEFAULT_ROLLING_PATTERN);
    }

    public TimedRollingFileAppender(String file, int bufferSize) {
        this(file, bufferSize, DEFAULT_ROLLING_PATTERN);
    }

    public TimedRollingFileAppender(String file, String pattern) {
        this(file, DEFAULT_BUFFER, pattern);
    }

    public TimedRollingFileAppender(String file, int bufferSize, String pattern) {
        this(file, bufferSize, pattern, DEFAULT_LOG_RESERVE_DAY);
    }
    
    public TimedRollingFileAppender(String file, String pattern, int reserve) {
        this(file, DEFAULT_BUFFER, pattern, reserve);
    }

    public TimedRollingFileAppender(String file, int bufferSize, String pattern, int reserve) {
        super(file, bufferSize);

        this.pattern = pattern;
        this.reserve = reserve;

        calendar = new RollingCalendar();
        calendar.setType(computeCheckPeriod());
        
        format = new SimpleDateFormat(pattern);

        nextFileName = fillFileName(new Date(this.file.lastModified()));
        
        TracerClear.watch(this);
    }

    /** 
     * @see com.yunxi.common.tracer.appender.RollingFileAppender#rollOver()
     */
    @Override
    protected void rollOver() {
        if (pattern == null) {
            System.err.println("没有滚动的模式");
            return;
        }

        String newFileName = fillFileName(currentDate);
        if (nextFileName.equals(newFileName)) {
            return;
        }

        try {
            buffer.close();
        } catch (IOException e) {
            System.err.println("关闭输出流失败" + e.getMessage());
        }

        File target = new File(nextFileName);
        if (target.exists()) {
            target.delete();
        }

        if (!file.renameTo(target)) {
            System.err.println("无法将文件名：" + fileName + " -> " + nextFileName);
        }

        this.initialize();
        
        nextFileName = newFileName;
    }

    /** 
     * @see com.yunxi.common.tracer.appender.RollingFileAppender#checkRollOver()
     */
    @Override
    protected boolean checkRollOver() {
        long currentMils = DateUtils.currentMills();
        if (currentMils >= nextRollOver) {
            currentDate.setTime(currentMils);
            nextRollOver = calendar.getNextCheckMillis(currentDate);
            return true;
        }
        return false;
    }

    /** 
     * @see com.yunxi.common.tracer.appender.TracerAppender#clear()
     */
    @Override
    public void clear() {
        try {
            File parent = file.getParentFile();
            if (parent == null) {
                return;
            }
            if (!parent.isDirectory()) {
                return;
            }

            File[] files = filterFiles(parent, file.getName());

            if (ArrayUtils.isEmpty(files)) {
                return;
            }

            for (File file : files) {
                String fileName = file.getName();
                int lastDot = fileName.lastIndexOf(".");
                if (lastDot < 0) {
                    continue;
                }

                String time = fileName.substring(lastDot);
                if (".log".equalsIgnoreCase(time)) {
                    continue;
                }
                
                Date date = DateUtils.parse(time, DAILY_ROLLING_PATTERN);
                if (date == null) {
                    date = DateUtils.parse(time, HOURLY_ROLLING_PATTERN);
                }
                if (date == null) {
                    continue;
                }

                Calendar now = Calendar.getInstance();
                now.add(Calendar.DATE, 0 - reserve);
                now.set(Calendar.HOUR, 0);
                now.set(Calendar.MINUTE, 0);
                now.set(Calendar.SECOND, 0);
                now.set(Calendar.MILLISECOND, 0);

                Calendar compareCal = Calendar.getInstance();
                compareCal.clear();
                compareCal.set(Calendar.YEAR, now.get(Calendar.YEAR));
                compareCal.set(Calendar.MONTH, now.get(Calendar.MONTH));
                compareCal.set(Calendar.DATE, now.get(Calendar.DATE));

                Calendar logCal = Calendar.getInstance();
                logCal.setTime(date);

                if (!logCal.before(compareCal)) {
                    continue;
                }

                boolean success = file.delete() && !file.exists();

                if (success) {
                } else {
                }
            }
        } catch (Throwable e) {
            // TODO log
        }
    }

    /**
     * 组装新的文件名
     * @param date
     * @return
     */
    private String fillFileName(Date date) {
        return fileName + format.format(date);
    }
    
    /**
     * 过滤文件
     * @param parent
     * @param baseName
     * @return
     */
    private File[] filterFiles(final File parent, final String baseName) {
        return parent.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return StringUtils.startsWith(name, baseName);
            }
        });
    }
    
    /**
     * 计算日志滚动类型
     * @return
     */
    private int computeCheckPeriod() {
        if (pattern != null) {
            Date epoch = new Date(0);
            RollingCalendar calendar = new RollingCalendar(DateUtils.DEFAULT_TIMEZONE);
            for (int i = TOP_OF_SECONDS; i <= TOP_OF_MONTH; i++) {
                calendar.setType(i);
                String r0 = DateUtils.format(epoch, pattern);
                long nextCheckMills = calendar.getNextCheckMillis(epoch);
                String r1 = DateUtils.format(nextCheckMills, pattern);
                if (r0 != null && r1 != null && !r0.equals(r1)) {
                    return i;
                }
            }
        }
        return TOP_OF_TROUBLE;
    }
}

class RollingCalendar extends GregorianCalendar {

    private static final long serialVersionUID = 2330372692702901277L;
    
    int type = TimedRollingFileAppender.TOP_OF_TROUBLE;

    RollingCalendar() {
        super();
    }

    RollingCalendar(TimeZone tz) {
        super(tz, Locale.getDefault());
    }

    RollingCalendar(TimeZone tz, Locale locale) {
        super(tz, locale);
    }

    void setType(int type) {
        this.type = type;
    }

    public long getNextCheckMillis(Date date) {
        return getNextCheckDate(date).getTime();
    }

    public Date getNextCheckDate(Date date) {
        setTime(date);

        switch (type) {
            case TimedRollingFileAppender.TOP_OF_SECONDS:
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.SECOND, 1);
                break;
            case TimedRollingFileAppender.TOP_OF_MINUTE:
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.MINUTE, 1);
                break;
            case TimedRollingFileAppender.TOP_OF_HOUR:
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.HOUR_OF_DAY, 1);
                break;
            case TimedRollingFileAppender.HALF_DAY:
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                int hour = get(Calendar.HOUR_OF_DAY);
                if (hour < 12) {
                    this.set(Calendar.HOUR_OF_DAY, 12);
                } else {
                    this.set(Calendar.HOUR_OF_DAY, 0);
                    this.add(Calendar.DAY_OF_MONTH, 1);
                }
                break;
            case TimedRollingFileAppender.TOP_OF_DAY:
                this.set(Calendar.HOUR_OF_DAY, 0);
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.DATE, 1);
                break;
            case TimedRollingFileAppender.TOP_OF_WEEK:
                this.set(Calendar.DAY_OF_WEEK, getFirstDayOfWeek());
                this.set(Calendar.HOUR_OF_DAY, 0);
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case TimedRollingFileAppender.TOP_OF_MONTH:
                this.set(Calendar.DATE, 1);
                this.set(Calendar.HOUR_OF_DAY, 0);
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.MONTH, 1);
                break;
            default:
                throw new IllegalStateException("Unknown Period type.");
        }

        return getTime();
    }
}