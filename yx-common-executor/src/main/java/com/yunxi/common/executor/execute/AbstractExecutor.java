package com.yunxi.common.executor.execute;

import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: AbstractExecutor.java, v 0.1 2019年1月9日 上午10:14:47 leukony Exp $
 */
public class AbstractExecutor<T extends AbstractExecutorCmd> implements InitializingBean {

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
    }
}