package com.yunxi.common.tracer.constants;

/**
 * Rpc服务结果码
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: RpcResultCode.java, v 0.1 2017年2月28日 下午5:42:19 leukony Exp $
 */
public enum RpcResultCode {

    /** 返回成功 */
    RPC_RESULT_SUCCESS("00"),

    /** 业务失败 */
    RPC_RESULT_BIZ_FAILED("01"),

    /** RPC逻辑失败 */
    RPC_RESULT_RPC_FAILED("02"),

    /** 超时失败 */
    RPC_RESULT_TIMEOUT_FAILED("03"), ;

    /** 结果码 */
    private String code;

    private RpcResultCode(String code) {
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
     * 根据结果码获取RpcResultCode
     * @param code
     * @return
     */
    public static RpcResultCode getRpcResultCode(String code) {
        RpcResultCode[] rpcResultCodes = RpcResultCode.values();
        for (RpcResultCode rpcResultCode : rpcResultCodes) {
            if (rpcResultCode.code.equals(code)) {
                return rpcResultCode;
            }
        }
        return null;
    }
}