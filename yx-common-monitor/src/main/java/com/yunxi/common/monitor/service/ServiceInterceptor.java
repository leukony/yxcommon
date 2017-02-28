package com.yunxi.common.monitor.service;

import org.aopalliance.intercept.MethodInvocation;

import com.yunxi.common.monitor.AppMonitorInterceptor;

/**
 * 服务调用监控日志拦截器基类
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: ServiceInterceptor.java, v 0.1 2016年12月26日 下午4:52:45 leukony Exp $
 */
public abstract class ServiceInterceptor extends AppMonitorInterceptor {
	
	@Override
	public Object doInvoke(MethodInvocation invocation) throws Throwable {
		long start = System.currentTimeMillis();
		
		// 返回结果
        Object result = null;
        // 设置调用唯一标识
        String rpcId = getOrSetRpcId();
		try {
			// 执行目标服务方法
			result = invocation.proceed();
			// 如果这里没有到达，说明已经抛出了异常，未正常响应请求
            // 可以通过result是否为null来判断服务是否正常
			return result;
		} catch (Throwable t) {
			// 调用发生异常时的处理
			result = onException(invocation, t, rpcId);
			return result;
		} finally {
			// 计算服务调用耗时
			long time = System.currentTimeMillis() - start;
			// 打印调用摘要日志
			try {
				digest(invocation, time, rpcId, result);
			} catch (Throwable t) {
				// 捕获异常，避免因为打印日志而造成远程调用失败
				APP_ERROR.error("打印摘要日志异常", t);
			}
		}
	}
	
	/**
	 * 提供给子类扩展功能：获取或设置远程调用唯一标识<p>
	 * 获取唯一标识，若未获取到，则设置远程调用唯一标识
	 * @return
	 */
	protected abstract String getOrSetRpcId();
	
	/**
	 * 提供给子类扩展功能：打印服务调用日志<p>
	 * @param invocation 调用信息
	 * @param time 耗时
	 * @param rpcId 远程调用唯一标识
	 * @param result 返回结果
	 */
	protected abstract void digest(MethodInvocation invocation, long time, String rpcId, Object result);

    /** 
     * 提供给子类扩展功能：远程调用异常处理<p>
     * 典型的场景:在拦截接口调用异常的同时输出日志，避免2个拦截器来实现这类需求。也可以在本方法提供接口调用异常后的返回值
     * @param invocation 调用信息
     * @param t 异常信息
     * @param rpcId 远程调用唯一标识
     */
    protected Object onException(MethodInvocation invocation, Throwable t, String rpcId) throws Throwable {
        throw t;
    }
}