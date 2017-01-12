package com.yunxi.common.monitor.service.dubbo;

import static com.yunxi.common.monitor.AppMonitorConstants.RPCID;
import static com.yunxi.common.monitor.AppMonitorConstants.UNKNOW;

import org.apache.commons.lang.StringUtils;

import com.alibaba.dubbo.rpc.RpcContext;
import com.yunxi.common.monitor.service.ServiceInterceptor;
import com.yunxi.common.tracer.util.TraceIdGenerator;

/**
 * Dubbo服务调用监控日志拦截器基类
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: DubboServiceInterceptor.java, v 0.1 2016年12月26日 下午4:52:45 leukony Exp $
 */
public abstract class DubboServiceInterceptor extends ServiceInterceptor {
    
    /**
     * 获取或设置远程调用唯一标识<p>
     * 获取唯一标识，若未获取到，则设置远程调用唯一标识
     * @return
     */
    @Override
    protected final String getOrSetRpcId() {
        String rpcId = UNKNOW;
        try {
            RpcContext rc = RpcContext.getContext();
            rpcId = rc.getAttachment(RPCID);
            if (StringUtils.isBlank(rpcId)) {
                rpcId = TraceIdGenerator.generate();
            }
            rc.setAttachment(RPCID, rpcId);
        } catch (Throwable t) {
            // 捕获异常，避免因为获取远程调用唯一标识而造成远程调用失败
            APP_ERROR.error("获取远程调用唯一标识异常", t);
        }
        return rpcId;
    }
}