package com.yunxi.common.monitor;

import static com.yunxi.common.monitor.AppMonitorConstants.APP_ERROR_LOGGER;
import static com.yunxi.common.monitor.AppMonitorConstants.UNKNOW;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 监控日志拦截器基类<p>
 * 用以排除Object原生方法；防止底层框架toString方法调用出错<p>
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: AppMonitorInterceptor.java, v 0.1 2016年12月26日 下午4:52:45 leukony Exp $
 */
public abstract class AppMonitorInterceptor implements MethodInterceptor, InitializingBean {
	
	/** 应用错误日志 */
    protected static final Logger APP_ERROR = LoggerFactory.getLogger(APP_ERROR_LOGGER);
	
	/** Object原生方法 */
    public static final Set<String> OBJECT_METHOD = new HashSet<String>();
    
    /** 应用监控配置 */
    protected AppMonitorConfig appMonitorConfig;

	@Override
	public final Object invoke(MethodInvocation invocation) throws Throwable {
		// 过滤对Object原生方法调用的拦截
        if (isObjectMethod(getMethodName(invocation))) {
            return invocation.proceed();
        }
        return doInvoke(invocation);
	}
	
	/**
     * 是否为Object原生方法
     * 
     * @param methodName
     * @return
     */
	protected final boolean isObjectMethod(String methodName) {
        return OBJECT_METHOD.contains(methodName);
    }
	
	/**
     * 获取调用完整名<br>
     * 类名 + "." + 方法
     * 
     * @param invocation
     * @return
     */
    protected final String getFullName(MethodInvocation invocation) {
    	Method method = invocation.getMethod();
    	StringBuilder sb = new StringBuilder();
    	sb.append(method.getDeclaringClass().getName());
    	sb.append(".");
    	sb.append(method.getName());
        return sb.toString();
    }
    
    /**
     * 获取调用类名
     * 
     * @param invocation
     * @return
     */
    protected final String getClassName(MethodInvocation invocation) {
        return invocation.getMethod().getDeclaringClass().getSimpleName();
    }

    /**
     * 获取调用方法名
     * 
     * @param invocation
     * @return
     */
    protected final String getMethodName(MethodInvocation invocation) {
        return invocation.getMethod().getName();
    }
    
    /**
     * 转换参数
     * 
     * @param arguments 参数
     * @return 参数转换后的结果
     */
    protected final String convertArgs(Object[] arguments) {
        try {
            if (arguments == null || arguments.length == 0) {
                return UNKNOW;
            }
            
            List<Object> list =  new ArrayList<Object>(arguments.length);
            for (Object argument : arguments) {
                // 使用对象描述支持，防止只打印内存地址
                // 排除JAVA本身的对象，只打印本系统定义对象
                if (needReflection(argument)) {
                    try {
                        argument = reflectionToString(argument, SHORT_PREFIX_STYLE);
                    } catch (Throwable t) {
                    	APP_ERROR.error("转换参数异常", t);
                    }
                }
                list.add(argument);
            }
            return list.toString();
        } catch (Throwable t) {
        	APP_ERROR.error("转换参数异常", t);
        	return UNKNOW;
        }
    }
    
    /**
     * 判断参数是否需要反射<br>
     * 
     * <ol>
     * <li>数组需要反射</li>
     * <li>非JDK原生类型需要反射</li>
     * </ol>
     * @param argument 参数
     * @return 是否需要反射
     */
    protected final boolean needReflection(Object argument) {
        if (argument == null) {
            return false;
        }
        
        // 原生类型直接返回不需要处理
        if (argument instanceof Byte
        	|| argument instanceof Short 
        	|| argument instanceof Integer 
        	|| argument instanceof Long 
        	|| argument instanceof Float 
        	|| argument instanceof Double 
        	|| argument instanceof Character 
        	|| argument instanceof String 
        	|| argument instanceof Boolean 
        	|| argument instanceof Date) {
        	return false;
        }
        
        return true;
    }
    
    /**
     * 具体拦截逻辑
     * 
     * @param invocation 调用信息
     * @return 服务调用结果
     * @throws Throwable
     */
    public abstract Object doInvoke(MethodInvocation invocation) throws Throwable;
    
    @Override
    public final void afterPropertiesSet() throws Exception {
        for (Method method : Object.class.getMethods()) {
            OBJECT_METHOD.add(method.getName());
        }
    }
    
    /**
     * Setter method for property <tt>appMonitorConfig</tt>.
     * 
     * @param appMonitorConfig value to be assigned to property appMonitorConfig
     */
    public final void setAppMonitorConfig(AppMonitorConfig appMonitorConfig) {
		this.appMonitorConfig = appMonitorConfig;
	}
}