package com.yunxi.common.executor.database;

/**
 * 执行器命令DAO
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: ExecutorCmdDAO.java, v 0.1 2019年1月22日 上午11:26:33 leukony Exp $
 */
public interface ExecutorCmdDAO {

    /**
     * 保存命令
     * @param executorCmd
     */
    void save(ExecutorCmdDO executorCmd);

    /**
     * 修改命令
     * @param executorCmd
     */
    void update(ExecutorCmdDO executorCmd);

    /**
     * 查询命令
     * @param id
     */
    ExecutorCmdDO selectById(Long id, String tableNamePrefix);

    /**
     * 删除命令
     * @param id
     */
    void deleteById(Long id, String tableNamePrefix);

}