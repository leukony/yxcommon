package com.yunxi.common.tracer.context;

/**
 * Rpc服务日志上下文
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: RpcContext.java, v 0.1 2017年2月28日 下午4:57:44 leukony Exp $
 */
public class RpcContext extends TracerContext<RpcContext> {

    /** 
     * @see com.yunxi.common.tracer.context.TracerContext#def()
     */
    @Override
    public RpcContext def() {
        return null;
    }

    /** 
     * @see com.yunxi.common.tracer.context.TracerContext#clone()
     */
    @Override
    public RpcContext clone() {
        return null;
    }

    /** 
     * @see com.yunxi.common.tracer.context.TracerContext#isSuccess()
     */
    @Override
    public boolean isSuccess() {
        return false;
    }
}