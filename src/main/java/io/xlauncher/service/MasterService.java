package io.xlauncher.service;

import io.xlauncher.entity.KeyEntity;

/**
 * 支持Master的服务
 */
public interface MasterService {

    /**
     * 设置Key与Value值,后端线程注册
     * @return
     * @throws Exception
     */
    void registryMasterService()throws Exception;

    /**
     * 监控Key的Node的变化,后端线程注册
     * @return
     * @throws Exception
     */
    void watcherKWorkerService()throws Exception;

    /**
     * 删除Master service注册的信息,后端线程注册
     * @throws Exception
     */
    void deleteMasterService()throws Exception;

    /**
     * 注册Master service,通过前端ajax请求进行注册
     * @return
     * @throws Exception
     */
    KeyEntity registryMasterServices()throws Exception;

    /**
     * 删除Master service的注册信息，通过前端ajax请求删除
     * @return
     * @throws Exception
     */
    KeyEntity deleteMasterServices()throws Exception;

    /**
     * 监控worker service的服务信息，通过前端ajax进行请求
     * @return
     * @throws Exception
     */
    KeyEntity watcherKWorkerServices()throws Exception;
}
