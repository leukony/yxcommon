package com.yunxi.common.tracer.constants;

/**
 * Rpc类型
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: RpcType.java, v 0.1 2017年3月1日 下午3:00:17 leukony Exp $
 */
public enum RpcType {

    /** 同步调用 */
    SYNC("00"),

    /** 异步调用 */
    ASYNC("01"),
    
    /** 有去无回 */
    ONEWAY("02"),

    /** 回调调用 */
    CALLBACK("03"), ;

    /** 类型 */
    private String type;

    private RpcType(String type) {
        this.type = type;
    }

    /**
      * Getter method for property <tt>type</tt>.
      * 
      * @return property value of type
      */
    public String getType() {
        return type;
    }

    /**
     * 根据类型获取RpcType
     * @param code
     * @return
     */
    public static RpcType getRpcType(String type) {
        RpcType[] rpcTypes = RpcType.values();
        for (RpcType rpcType : rpcTypes) {
            if (rpcType.type.equals(type)) {
                return rpcType;
            }
        }
        return null;
    }
}