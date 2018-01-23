package io.xlauncher.dao;

import io.xlauncher.entity.KeyEntity;

/**
 * 用于请求etcd的RESTful接口
 */
public interface MasterDao {

    /**
     * 设置Key与其Value值
     * @param url
     * @param parames
     * @return
     * @throws Exception
     */
    KeyEntity setKNodeInfos(String url, Object[] parames)throws Exception;

    /**
     * 监控key的变化
     * @param url
     * @param parames
     * @return
     * @throws Exception
     */
    KeyEntity watcherNodeInfos(String url, Object[] parames)throws Exception;

    /**
     * 删除Master service的注册信息
     * @param url
     * @return
     * @throws Exception
     */
    KeyEntity deleteMasterService(String url)throws Exception;
}
