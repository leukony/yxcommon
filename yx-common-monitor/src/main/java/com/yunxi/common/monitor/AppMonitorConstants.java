package com.yunxi.common.monitor;

/**
 * 监控常量
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: AppMonitorConstants.java, v 0.1 2016年12月26日 下午4:52:45 leukony Exp $
 */
public class AppMonitorConstants {
	
	/**SEPARATOR*/
	/** 未知标志 */
	public static final String UNKNOW 									= "-";

	/** 远程调用唯一标识 */
	public static final String RPCID 									= "YXRPCID";
	
	
	
	/**LOGGER*/
	/** 应用错误日志 */
	public static final String APP_ERROR_LOGGER 						= "APP-ERROR-LOGGER";
	
	/** 应用服务被调用日志 */
	public static final String APP_SERVICE_IN_LOGGER 					= "APP-SERVICE-IN-LOGGER";
	
	/** 应用调用外部服务日志 */
	public static final String APP_SERVICE_OUT_LOGGER	 				= "APP-SERVICE-OUT-LOGGER";
	
	
	
	/**TEMPLATE*/
	/**
 	 * 服务被调用异常日志信息模板, 异常预期：是否成功标示为"E"<p>
     * 格式 [(请求标识,类名,方法名,是否成功标志,耗时,线程名-线程ID)(调用的IP)(关键参数列表)(错误码,错误描述)(返回值个数)] 
     */
	public static final String SERVICE_IN_EXCEPTION_MSG_TEMPLATE		= "[({0},{1},{2},E,{3},{4}-{5})({6},{7})({8})(-,-)(0)]";

    /** 
     * 服务被调用成功日志信息模板, 符合预期：是否成功标示为"Y"<p>
     * 格式 [(请求标识,类名,方法名,是否成功标志,耗时,线程名-线程ID)(调用的IP)(关键参数列表)(错误码,错误描述)(返回值个数)]
     */
	public static final String SERVICE_IN_SUCCESS_MSG_TEMPLATE 			= "[({0},{1},{2},Y,{3},{4}-{5})({6},{7})({8})(-,-)({9})]";

    /** 
     * 服务被调用失败日志信息模板, 非法预期：是否成功标示为"N"<p>
     * 格式 [(请求标识,类名,方法名,是否成功标志,耗时,线程名-线程ID)(调用的IP)(关键参数列表)(错误码,错误描述)(返回值个数)]
     */
	public static final String SERVICE_IN_FAIL_MSG_TEMPLATE    			= "[({0},{1},{2},N,{3},{4}-{5})({6},{7})({8})({9},{10})(0)]";
	
	/**
 	 * 调用外部服务异常日志信息模板, 异常预期：是否成功标示为"E"<p>
     * 格式 [(请求标识,类名,方法名,是否成功标志,耗时,线程名-线程ID)(关键参数列表)(错误码,错误描述)(返回值个数)] 
     */
	public static final String SERVICE_OUT_EXCEPTION_MSG_TEMPLATE		= "[({0},{1},{2},E,{3},{4}-{5})({6})(-,-)(0)]";

    /** 
     * 调用外部服务成功日志信息模板, 符合预期：是否成功标示为"Y"<p>
     * 格式 [(请求标识,类名,方法名,是否成功标志,耗时,线程名-线程ID)(关键参数列表)(错误码,错误描述)(返回值个数)]
     */
	public static final String SERVICE_OUT_SUCCESS_MSG_TEMPLATE 		= "[({0},{1},{2},Y,{3},{4}-{5})({6})(-,-)({7})]";

    /** 
     * 调用外部服务失败日志信息模板, 非法预期：是否成功标示为"N"<p>
     * 格式 [(请求标识,类名,方法名,是否成功标志,耗时,线程名-线程ID)(关键参数列表)(错误码,错误描述)(返回值个数)]
     */
	public static final String SERVICE_OUT_FAIL_MSG_TEMPLATE    		= "[({0},{1},{2},N,{3},{4}-{5})({6})({7},{8})(0)]";
}