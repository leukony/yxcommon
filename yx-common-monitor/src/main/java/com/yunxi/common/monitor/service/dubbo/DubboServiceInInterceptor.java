package com.yunxi.common.monitor.service.dubbo;

import static com.yunxi.common.monitor.AppMonitorConstants.APP_SERVICE_IN_LOGGER;
import static com.yunxi.common.monitor.AppMonitorConstants.SERVICE_IN_EXCEPTION_MSG_TEMPLATE;
import static com.yunxi.common.monitor.AppMonitorConstants.SERVICE_IN_FAIL_MSG_TEMPLATE;
import static com.yunxi.common.monitor.AppMonitorConstants.SERVICE_IN_SUCCESS_MSG_TEMPLATE;
import static com.yunxi.common.monitor.AppMonitorConstants.UNKNOW;
import static java.text.MessageFormat.format;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.RpcContext;
import com.yunxi.common.rpc.result.Result;
import com.yunxi.common.rpc.result.ResultWrapper;

/**
 * 服务被调用监控日志拦截器
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: DubboServiceInInterceptor.java, v 0.1 2016年12月26日 下午4:52:45 leukony Exp $
 */
public class DubboServiceInInterceptor extends DubboServiceInterceptor {
	
	/** 应用服务被调用日志 */
    protected static final Logger SERVICE_IN = LoggerFactory.getLogger(APP_SERVICE_IN_LOGGER);
    
    /** 系统未知错误码 */
    private String defaultErrCode;
    
    @Override
    protected Object onException(MethodInvocation invocation, Throwable t, String rpcId) throws Throwable {
    	APP_ERROR.error(format("服务异常：{0}:{1}", getFullName(invocation), rpcId), t);
    	return ResultWrapper.failure(defaultErrCode, "service has error");
    }
	
	@Override
	protected void digest(MethodInvocation invocation, long time, String rpcId, Object result) {
		if (!appMonitorConfig.isServiceInSwitch()) {
			return;
		}
		
		Thread curThread = Thread.currentThread();
    	String threadName = curThread.getName();
    	String threadId = String.valueOf(curThread.getId());
    	
    	RpcContext rc = RpcContext.getContext();
    	rpcId = StringUtils.isBlank(rpcId) ? UNKNOW : rpcId;
    	
    	String hostIp = UNKNOW;
    	String hostName = UNKNOW;
    	InetSocketAddress isa = rc.getRemoteAddress();
        if (isa != null) {
            InetAddress ia = isa.getAddress();
            if (ia != null) {
            	hostIp = ia.getHostAddress();
                hostName = isa.getHostName();
            }
        }
    	hostIp = StringUtils.isBlank(hostIp) ? UNKNOW : hostIp;
    	hostName = StringUtils.isBlank(hostName) ? UNKNOW : hostName;
    	
		if (result == null) {
			// 到达这个分支 ，说明处理请求过程中，发生了异常
			if (SERVICE_IN.isInfoEnabled()) {
				SERVICE_IN.info(format(SERVICE_IN_EXCEPTION_MSG_TEMPLATE, rpcId, getClassName(invocation), 
			        	getMethodName(invocation), String.valueOf(time), threadName, threadId, hostIp, hostName, 
			        	getServiceDigestParam(invocation.getArguments())));
			}
	        return;
		}
		
		Result<?> rpcResult = (Result<?>) result; 
    	String errCode = rpcResult.getErrCode();
    	errCode = StringUtils.isBlank(errCode) ? UNKNOW : errCode;
    	String errMsg = rpcResult.getErrMsg();
    	errMsg = StringUtils.isBlank(errMsg) ? UNKNOW : errMsg;
        
    	if (rpcResult.isSuccess()) {
    		if (SERVICE_IN.isInfoEnabled()) {
    			SERVICE_IN.info(format(SERVICE_IN_SUCCESS_MSG_TEMPLATE, rpcId, getClassName(invocation), 
    					getMethodName(invocation), String.valueOf(time), threadName, threadId, hostIp, hostName, 
    					getServiceDigestParam(invocation.getArguments()), 0));
    		}
    		return;
        } else {
        	if (SERVICE_IN.isInfoEnabled()) {
        		SERVICE_IN.info(format(SERVICE_IN_FAIL_MSG_TEMPLATE, rpcId, getClassName(invocation), 
        				getMethodName(invocation), String.valueOf(time), threadName, threadId, hostIp, hostName, 
        				getServiceDigestParam(invocation.getArguments()), errCode, errMsg));
        	}
        	return;
        }
	}
	
	/**
	 * 获取日志打印的参数值
	 * @param arguments
	 * @return
	 */
	protected String getServiceDigestParam(Object[] arguments) {
		return appMonitorConfig.isServiceInParamSwitch() ? convertArgs(arguments) : UNKNOW;
	}
	
	/**
     * Setter method for property <tt>defaultErrCode</tt>.
     * 
     * @param defaultErrCode value to be assigned to property defaultErrCode
     */
	public void setDefaultErrCode(String defaultErrCode) {
		this.defaultErrCode = defaultErrCode;
	}
}