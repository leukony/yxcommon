package com.yunxi.common.monitor.service.dubbo;

import static com.yunxi.common.monitor.AppMonitorConstants.APP_SERVICE_OUT_LOGGER;
import static com.yunxi.common.monitor.AppMonitorConstants.SERVICE_OUT_EXCEPTION_MSG_TEMPLATE;
import static com.yunxi.common.monitor.AppMonitorConstants.SERVICE_OUT_FAIL_MSG_TEMPLATE;
import static com.yunxi.common.monitor.AppMonitorConstants.SERVICE_OUT_SUCCESS_MSG_TEMPLATE;
import static com.yunxi.common.monitor.AppMonitorConstants.UNKNOW;
import static java.text.MessageFormat.format;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunxi.common.rpc.result.Result;

/**
 * 调用外部服务监控日志拦截器
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: DubboServiceOutInterceptor.java, v 0.1 2016年12月26日 下午4:52:45 leukony Exp $
 */
public class DubboServiceOutInterceptor extends DubboServiceInterceptor {
	
	/** 应用调用外部服务日志 */
    protected static final Logger SERVICE_OUT = LoggerFactory.getLogger(APP_SERVICE_OUT_LOGGER);
	
	@Override
	protected void digest(MethodInvocation invocation, long time, String rpcId, Object result) {
		if (!appMonitorConfig.isServiceOutSwitch()) {
			return;
		}
		
		Thread curThread = Thread.currentThread();
    	String threadName = curThread.getName();
    	String threadId = String.valueOf(curThread.getId());
    	
    	rpcId = StringUtils.isBlank(rpcId) ? UNKNOW : rpcId;
    	
		if (result == null) {
			// 到达这个分支 ，说明处理请求过程中，发生了异常
			if (SERVICE_OUT.isInfoEnabled()) {
				SERVICE_OUT.info(format(SERVICE_OUT_EXCEPTION_MSG_TEMPLATE, rpcId, getClassName(invocation), 
			        	getMethodName(invocation), String.valueOf(time), threadName, threadId,
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
    		if (SERVICE_OUT.isInfoEnabled()) {
    			SERVICE_OUT.info(format(SERVICE_OUT_SUCCESS_MSG_TEMPLATE, rpcId, getClassName(invocation), 
    					getMethodName(invocation), String.valueOf(time), threadName, threadId,
    					getServiceDigestParam(invocation.getArguments()), 0));
    		}
    		return;
        } else {
        	if (SERVICE_OUT.isInfoEnabled()) {
        		SERVICE_OUT.info(format(SERVICE_OUT_FAIL_MSG_TEMPLATE, rpcId, getClassName(invocation), 
        				getMethodName(invocation), String.valueOf(time), threadName, threadId,
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
		return appMonitorConfig.isServiceOutParamSwitch() ? convertArgs(arguments) : UNKNOW;
	}
}