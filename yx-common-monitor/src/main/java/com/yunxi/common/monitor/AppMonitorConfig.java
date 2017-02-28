package com.yunxi.common.monitor;

/**
 * 监控配置
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: AppMonitorConfig.java, v 0.1 2016年12月26日 下午4:52:45 leukony Exp $
 */
public class AppMonitorConfig {

	/** 
	 * 服务被调用摘要日志打印开关，默认打印<br>
	 * 
	 * <ol>
	 * <li>true：摘要日志打印</li>
	 * <li>false：摘要日志不打印</li>
	 * </ol>
	 */
	private boolean serviceInSwitch  		= true;
	
	/** 
	 * 服务被调用摘要日志打印参数开关，默认打印<br>
	 * 服务被调用摘要日志打印开关打开时，此配置项才生效<br>
	 * 
	 * <ol>
	 * <li>true：摘要日志打印参数</li>
	 * <li>false：摘要日志不打印参数</li>
	 * </ol>
	 */
	private boolean serviceInParamSwitch	= true;
	
	/** 
	 * 调用外部服务摘要日志打印开关，默认打印<br>
	 * 
	 * <ol>
	 * <li>true：摘要日志打印</li>
	 * <li>false：摘要日志不打印</li>
	 * </ol>
	 */
	private boolean serviceOutSwitch  		= true;
	
	/** 
	 * 调用外部服务摘要日志打印参数开关，默认打印<br>
	 * 调用外部服务摘要日志打印开关打开时，此配置项才生效<br>
	 * 
	 * <ol>
	 * <li>true：摘要日志打印参数</li>
	 * <li>false：摘要日志不打印参数</li>
	 * </ol>
	 */
	private boolean serviceOutParamSwitch	= true;

	/**
     * Getter method for property <tt>serviceInSwitch</tt>.
     * 
     * @return property value of serviceInSwitch
     */
	public boolean isServiceInSwitch() {
		return serviceInSwitch;
	}

	/**
     * Setter method for property <tt>serviceInSwitch</tt>.
     * 
     * @param serviceInSwitch value to be assigned to property serviceInSwitch
     */
	public void setServiceInSwitch(boolean serviceInSwitch) {
		this.serviceInSwitch = serviceInSwitch;
	}

	/**
     * Getter method for property <tt>serviceInParamSwitch</tt>.
     * 
     * @return property value of serviceInParamSwitch
     */
	public boolean isServiceInParamSwitch() {
		return serviceInParamSwitch;
	}

	/**
     * Setter method for property <tt>serviceInParamSwitch</tt>.
     * 
     * @param serviceInParamSwitch value to be assigned to property serviceInParamSwitch
     */
	public void setServiceInParamSwitch(boolean serviceInParamSwitch) {
		this.serviceInParamSwitch = serviceInParamSwitch;
	}

	/**
     * Getter method for property <tt>serviceOutSwitch</tt>.
     * 
     * @return property value of serviceOutSwitch
     */
	public boolean isServiceOutSwitch() {
		return serviceOutSwitch;
	}

	/**
     * Setter method for property <tt>serviceOutSwitch</tt>.
     * 
     * @param serviceOutSwitch value to be assigned to property serviceOutSwitch
     */
	public void setServiceOutSwitch(boolean serviceOutSwitch) {
		this.serviceOutSwitch = serviceOutSwitch;
	}

	/**
     * Getter method for property <tt>serviceOutParamSwitch</tt>.
     * 
     * @return property value of serviceOutParamSwitch
     */
	public boolean isServiceOutParamSwitch() {
		return serviceOutParamSwitch;
	}

	/**
     * Setter method for property <tt>serviceOutParamSwitch</tt>.
     * 
     * @param serviceOutParamSwitch value to be assigned to property serviceOutParamSwitch
     */
	public void setServiceOutParamSwitch(boolean serviceOutParamSwitch) {
		this.serviceOutParamSwitch = serviceOutParamSwitch;
	}
}