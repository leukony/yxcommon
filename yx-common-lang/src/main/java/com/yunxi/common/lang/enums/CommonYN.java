package com.yunxi.common.lang.enums;

/**
 * YN
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: CommonYN.java, v 0.1 2017年2月6日 上午11:29:52 leukony Exp $
 */
public enum CommonYN {

    Y,
    N;

    /**
     * 根据参数获取枚举值
     * 
     * @param e
     * @return
     */
    public static boolean isY(String e) {
        if (Y.name().equalsIgnoreCase(e)
            || "YES".equalsIgnoreCase(e)
            || "TRUE".equalsIgnoreCase(e)) {
            return true;
        }
        
        if (N.name().equalsIgnoreCase(e)
            || "NO".equalsIgnoreCase(e)
            || "FALSE".equalsIgnoreCase(e)) {
            return false;
        }
        
        return false;
    }
    
    /**
     * 根据参数获取枚举值
     * 
     * @param e
     * @return
     */
    public static CommonYN get(String e) {
        if (Y.name().equalsIgnoreCase(e)
            || "YES".equalsIgnoreCase(e)
            || "TRUE".equalsIgnoreCase(e)) {
            return Y;
        }
        
        if (N.name().equalsIgnoreCase(e)
            || "NO".equalsIgnoreCase(e)
            || "FALSE".equalsIgnoreCase(e)) {
            return N;
        }
        
        return N;
    }
    
    /**
     * 根据参数获取枚举值
     * 
     * @param e
     * @return
     */
    public static CommonYN get(boolean e) {
        return e ? Y : N;
    }
}