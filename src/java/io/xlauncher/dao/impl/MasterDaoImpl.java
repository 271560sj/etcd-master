package io.xlauncher.dao.impl;

import io.xlauncher.dao.MasterDao;
import io.xlauncher.entity.KeyEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MasterDaoImpl implements MasterDao {

    //打印日志
    Log log = LogFactory.getLog(MasterDaoImpl.class);

    //HTTP请求的接口
    RestTemplate restTemplate = new RestTemplate();

    //注册Master service的信息
    public KeyEntity setKNodeInfos(String url, Object[] parames) throws Exception {
        KeyEntity entity = new KeyEntity();
        try {
            //构造请求消息头
            HttpHeaders headers = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(null, headers);
            //请求etcd
            ResponseEntity<KeyEntity> responseEntity =  restTemplate.exchange(url, HttpMethod.PUT,httpEntity,KeyEntity.class,parames);
            if (responseEntity.getStatusCodeValue() == 200 || responseEntity.getStatusCodeValue() == 201){
                entity = responseEntity.getBody();
            }
        }catch (Exception e){
            log.error("MasterDaoImpl,setKNodeInfos,Create or set key and values err",e);
        }finally {
            return entity;
        }
    }

    //监控worker service的信息
    public KeyEntity watcherNodeInfos(String url, Object[] parames) throws Exception {
        KeyEntity entity = new KeyEntity();

        try {
            //构造请求消息头
            HttpHeaders headers = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(null,headers);
            //请求etcd
            ResponseEntity<KeyEntity> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, KeyEntity.class, parames);
            if (responseEntity.getStatusCodeValue() == 200){
                entity = responseEntity.getBody();
            }
        }catch (Exception e){
            log.error("MasterDaoImpl,watcherNodeInfos,Watcher key info error",e);
        }finally {
            return entity;
        }
    }
}
