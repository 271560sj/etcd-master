package io.xlauncher.service;

/**
 * 支持Master的服务
 */
public interface MasterService {

    /**
     * 设置Key与Value值
     * @return
     * @throws Exception
     */
    void registryMasterService()throws Exception;

    /**
     * 监控Key的Node的变化
     * @return
     * @throws Exception
     */
    void watcherKWorkerService()throws Exception;

    /**
     * 删除Master service注册的信息
     * @throws Exception
     */
    void deleteMasterService()throws Exception;
}
