package io.xlauncher.dao.impl;

import com.alibaba.fastjson.JSONObject;
import io.xlauncher.dao.MasterDao;
import io.xlauncher.entity.KeyEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class MasterDaoImpl implements MasterDao {

    //打印日志
    private final static Log log = LogFactory.getLog(MasterDaoImpl.class);

    //HTTP请求的接口
    private RestTemplate restTemplate = new RestTemplate();

    //注册Master service的信息
    public KeyEntity setKNodeInfos(String url, Object[] parames) throws Exception {
        KeyEntity entity = new KeyEntity();
        try {
//            //构造请求消息头
//            HttpHeaders headers = new HttpHeaders();
//            HttpEntity httpEntity = new HttpEntity(null, headers);
//            //请求etcd
//            ResponseEntity<KeyEntity> responseEntity =  restTemplate.exchange(url, HttpMethod.PUT,httpEntity,KeyEntity.class,parames);
            ResponseEntity<KeyEntity> responseEntity = restRequest(url,HttpMethod.PUT,parames);
            if (responseEntity.getStatusCodeValue() == 200 || responseEntity.getStatusCodeValue() == 201){
                entity = responseEntity.getBody();
            }
        }catch (HttpClientErrorException e){
            entity = dealWithError(e,"MasterDaoImpl,setKNodeInfos,Create or set key and values err");
        }finally {
            return entity;
        }
    }

    //监控worker service的信息
    public KeyEntity watcherNodeInfos(String url) throws Exception {
        KeyEntity entity = new KeyEntity();

        try {
//            //构造请求消息头
//            HttpHeaders headers = new HttpHeaders();
//            HttpEntity httpEntity = new HttpEntity(null,headers);
//            //请求etcd
//            ResponseEntity<KeyEntity> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, KeyEntity.class);
            ResponseEntity<KeyEntity> responseEntity = restRequest(url,HttpMethod.GET,new Object[]{});
            if (responseEntity.getStatusCodeValue() == 200){
                entity = responseEntity.getBody();
            }
        }catch (HttpClientErrorException e){
            entity = dealWithError(e,"MasterDaoImpl,watcherNodeInfos,Watcher key info error");
        }finally {
            return entity;
        }
    }

    //删除Master service的注册信息
    public KeyEntity deleteMasterService(String url) throws Exception {

        KeyEntity entity = new KeyEntity();
        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity httpEntity = new HttpEntity(null, headers);
//
//            ResponseEntity<KeyEntity> responseEntity = restTemplate.exchange(url,HttpMethod.DELETE,httpEntity,KeyEntity.class);
            ResponseEntity<KeyEntity> responseEntity = restRequest(url,HttpMethod.DELETE,new Object[]{});
            if (responseEntity.getStatusCodeValue() == 200){
                entity = responseEntity.getBody();
            }
        }catch (HttpClientErrorException e){
            entity = dealWithError(e,"MasterDaoImpl,deleteMasterService,delete master service error.");
        }finally {
            return entity;
        }
    }

    //进行错误处理
    private KeyEntity dealWithError(HttpClientErrorException e, String message){
        String error = e.getResponseBodyAsString();
        KeyEntity entity = JSONObject.parseObject(error,KeyEntity.class);
        log.error(message,e);
        return entity;
    }

    //进行http请求
    private ResponseEntity<KeyEntity> restRequest(String url, HttpMethod method,Object[] parames){
        //构造请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(null,headers);
        //返回请求结果
        return restTemplate.exchange(url,method,httpEntity,KeyEntity.class,parames);
    }
}
