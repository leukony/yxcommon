package com.yunxi.common.executor.database;

import java.util.Date;

/**
 * 执行器命令实体
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: ExecutorCmdDO.java, v 0.1 2019年1月9日 下午1:49:42 leukony Exp $
 */
public class ExecutorCmdDO {

    /** 命令主键 */
    private Long   id;

    /** 命令编号 */
    private String cmdNo;

    /** 命令类型 */
    private String cmdType;

    /** 命令trace */
    private String cmdTrace;

    /** 命令状态 */
    private String status;

    /** 命令重试次数 */
    private int    executeTimes;

    /** 命令下次执行时间 */
    private Date   nextExecuteTime;

    /** 命令上下文 */
    private String context;

    /** 命令生成主机 */
    private String hostname;

    /** 命令创建时间 */
    private Date   gmtCreate;

    /** 命令最后修改时间 */
    private Date   gmtModify;

    /** 命令表前缀 */
    private String tableNamePrefix;

    /**
      * Getter method for property <tt>id</tt>.
      * 
      * @return property value of id
      */
    public Long getId() {
        return id;
    }

    /**
      * Setter method for property <tt>id</tt>.
      * 
      * @param id value to be assigned to property id
      */
    public void setId(Long id) {
        this.id = id;
    }

    /**
      * Getter method for property <tt>cmdNo</tt>.
      * 
      * @return property value of cmdNo
      */
    public String getCmdNo() {
        return cmdNo;
    }

    /**
      * Setter method for property <tt>cmdNo</tt>.
      * 
      * @param cmdNo value to be assigned to property cmdNo
      */
    public void setCmdNo(String cmdNo) {
        this.cmdNo = cmdNo;
    }

    /**
      * Getter method for property <tt>cmdType</tt>.
      * 
      * @return property value of cmdType
      */
    public String getCmdType() {
        return cmdType;
    }

    /**
      * Setter method for property <tt>cmdType</tt>.
      * 
      * @param cmdType value to be assigned to property cmdType
      */
    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    /**
      * Getter method for property <tt>cmdTrace</tt>.
      * 
      * @return property value of cmdTrace
      */
    public String getCmdTrace() {
        return cmdTrace;
    }

    /**
      * Setter method for property <tt>cmdTrace</tt>.
      * 
      * @param cmdTrace value to be assigned to property cmdTrace
      */
    public void setCmdTrace(String cmdTrace) {
        this.cmdTrace = cmdTrace;
    }

    /**
      * Getter method for property <tt>status</tt>.
      * 
      * @return property value of status
      */
    public String getStatus() {
        return status;
    }

    /**
      * Setter method for property <tt>status</tt>.
      * 
      * @param status value to be assigned to property status
      */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
      * Getter method for property <tt>executeTimes</tt>.
      * 
      * @return property value of executeTimes
      */
    public int getExecuteTimes() {
        return executeTimes;
    }

    /**
      * Setter method for property <tt>executeTimes</tt>.
      * 
      * @param executeTimes value to be assigned to property executeTimes
      */
    public void setExecuteTimes(int executeTimes) {
        this.executeTimes = executeTimes;
    }

    /**
      * Getter method for property <tt>nextExecuteTime</tt>.
      * 
      * @return property value of nextExecuteTime
      */
    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    /**
      * Setter method for property <tt>nextExecuteTime</tt>.
      * 
      * @param nextExecuteTime value to be assigned to property nextExecuteTime
      */
    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    /**
      * Getter method for property <tt>context</tt>.
      * 
      * @return property value of context
      */
    public String getContext() {
        return context;
    }

    /**
      * Setter method for property <tt>context</tt>.
      * 
      * @param context value to be assigned to property context
      */
    public void setContext(String context) {
        this.context = context;
    }

    /**
      * Getter method for property <tt>hostname</tt>.
      * 
      * @return property value of hostname
      */
    public String getHostname() {
        return hostname;
    }

    /**
      * Setter method for property <tt>hostname</tt>.
      * 
      * @param hostname value to be assigned to property hostname
      */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
      * Getter method for property <tt>gmtCreate</tt>.
      * 
      * @return property value of gmtCreate
      */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
      * Setter method for property <tt>gmtCreate</tt>.
      * 
      * @param gmtCreate value to be assigned to property gmtCreate
      */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
      * Getter method for property <tt>gmtModify</tt>.
      * 
      * @return property value of gmtModify
      */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
      * Setter method for property <tt>gmtModify</tt>.
      * 
      * @param gmtModify value to be assigned to property gmtModify
      */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
      * Getter method for property <tt>tableNamePrefix</tt>.
      * 
      * @return property value of tableNamePrefix
      */
    public String getTableNamePrefix() {
        return tableNamePrefix;
    }

    /**
      * Setter method for property <tt>tableNamePrefix</tt>.
      * 
      * @param tableNamePrefix value to be assigned to property tableNamePrefix
      */
    public void setTableNamePrefix(String tableNamePrefix) {
        this.tableNamePrefix = tableNamePrefix;
    }
}