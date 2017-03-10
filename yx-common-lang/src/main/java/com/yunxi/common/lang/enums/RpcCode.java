package com.yunxi.common.lang.enums;

/**
 * Rpc结果码
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: RpcCode.java, v 0.1 2017年2月28日 下午5:42:19 leukony Exp $
 */
public enum RpcCode {

    /** 返回成功 */
    RPC_SUCCESS("00"),

    /** 业务失败 */
    RPC_BIZ_FAILED("01"),
    
    /** 网络异常 */
    RPC_NETWORK_FAILED("02"),

    /** 超时失败 */
    RPC_TIMEOUT_FAILED("03"), ;

    /** 结果码 */
    private String code;

    private RpcCode(String code) {
        this.code = code;
    }

    /**
      * Getter method for property <tt>code</tt>.
      * 
      * @return property value of code
      */
    public String getCode() {
        return code;
    }

    /**
     * 根据结果码获取RpcCode
     * @param code
     * @return
     */
    public static RpcCode getRpcCode(String code) {
        RpcCode[] rpcCodes = RpcCode.values();
        for (RpcCode rpcCode : rpcCodes) {
            if (rpcCode.code.equals(code)) {
                return rpcCode;
            }
        }
        return null;
    }
}