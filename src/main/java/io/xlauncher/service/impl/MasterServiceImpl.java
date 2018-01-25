package io.xlauncher.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.xlauncher.dao.MasterDao;
import io.xlauncher.entity.KeyEntity;
import io.xlauncher.entity.RegistryEntity;
import io.xlauncher.service.MasterService;
import io.xlauncher.utils.ReadPropertiesUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class MasterServiceImpl implements MasterService{

    @Autowired
    MasterDao masterDao;

    //读取配置文件
    @Autowired
    ReadPropertiesUtils properties;

    //注册Master service的信息，通过线程进行，在后端运行（暂时没有实现线程与前端界面通信）
    public void registryMasterService() throws Exception {

        //获取URL与请求参数
        Map<String, Object> map = getRegistryUrlsAndParames();

        //开始线程运行
        RegistryMasterServiceThread setKeysThread = new RegistryMasterServiceThread((String)map.get("url"),(Object[])map.get("parames"));
        Thread thread = new Thread(setKeysThread);
        thread.start();
    }

    //监控worker service的信息,通过线程进行，在后端运行（暂时没有实现线程与前端界面通信）
    public void watcherKWorkerService() throws Exception {

        //运行线程
        WatcherWorkerServiceThread watcherKeysThread = new WatcherWorkerServiceThread(getWatcherUrls());
        Thread thread = new Thread(watcherKeysThread);
        thread.start();
    }

    //删除Master service的注册信息，通过后端线程进行删除（暂时没有实现线程与前端界面通信）
    public void deleteMasterService() throws Exception {
        DeleteMasterServiceThread deleteMasterServiceThread = new DeleteMasterServiceThread(getDeleteUrls());
        Thread thread = new Thread(deleteMasterServiceThread);
        thread.start();
    }

    //注册服务信息,前端ajax请求进行注册
    public KeyEntity registryMasterServices() throws Exception {

        //获取请求的URL与请求参数
        Map<String, Object> map = getRegistryUrlsAndParames();

        //进行请求
        KeyEntity entity = masterDao.setKNodeInfos((String) map.get("url"),(Object[]) map.get("parames"));
        return entity;
    }

    //删除maser service的注册信息，通过前端ajax请求进行
    public KeyEntity deleteMasterServices() throws Exception {
        KeyEntity entity = masterDao.deleteMasterService(getDeleteUrls());
        return entity;
    }

    //监控worker service的服务信息，通过前端ajax进行请求
    public KeyEntity watcherKWorkerServices() throws Exception {
        KeyEntity entity = masterDao.watcherNodeInfos(getWatcherUrls());
        return entity;
    }

    //构造Watcher的URL
    private String getWatcherUrls(){
        //构造请求的URL
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://").append(properties.getProperty("master.registry.etcd.ip") + ":"
                + properties.getProperty("master.registry.etcd.port"))
                .append("/v2/keys/").append(properties.getProperty("master.listen.wait.key"))
                .append("?wait=true")
                .append(Integer.parseInt(properties.getProperty("master.listen.wait.index")) == 0
                        ? "" : "&waitIndex=" + Integer.parseInt(properties.getProperty("master.listen.wait.index")))
                .append(Boolean.parseBoolean(properties.getProperty("master.listen.wait.recursive")) ? "&recursive=true" : "");
        return stringBuffer.toString();
    }

    //获取删除的URL
    private String getDeleteUrls(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://").append(properties.getProperty("master.registry.etcd.ip") + ":"
                + properties.getProperty("master.registry.etcd.port"))
                .append("/v2/keys/").append(properties.getProperty("master.registry.key"))
                .append(Boolean.parseBoolean(properties.getProperty("master.registry.recursive")) ? "?recursive=true"
                : (Boolean.parseBoolean(properties.getProperty("master.registry.dir")) ? "?dir=true" : ""));
        return stringBuffer.toString();
    }

    //构造URL与请求参数
    private Map<String, Object> getRegistryUrlsAndParames(){
        //map用于存储URL与请求参数
        Map<String, Object> map = new HashMap<String, Object>();

        //存储请求参数
        ArrayList<Object> list = new ArrayList<Object>();

        StringBuffer url = new StringBuffer();

        url.append("http://").append(properties.getProperty("master.registry.etcd.ip") + ":" + properties.getProperty("master.registry.etcd.port"))
                .append("/v2/keys/").append(properties.getProperty("master.registry.key")).append("?value={value}");

        //构造注册信息
        RegistryEntity entity = new RegistryEntity();
        entity.setServiceIP(properties.getProperty("master.registry.service.ip"));
        entity.setServicePort(properties.getProperty("master.registry.service.port"));
        entity.setServiceName(properties.getProperty("master.registry.service.name"));

        //添加到list集合
        list.add(JSONObject.toJSONString(entity));

        if (Integer.parseInt(properties.getProperty("master.registry.ttl")) > 0){
            url.append("&ttl={ttl}");//ttl设置Key的生存时间
            list.add(Integer.parseInt(properties.getProperty("master.registry.ttl")));
        }
        if (Boolean.parseBoolean(properties.getProperty("master.registry.dir")) == true){
            url.append("&dir={dir}");//设置Key是否为目录
            list.add(Boolean.parseBoolean(properties.getProperty("master.registry.dir")));
        }
        if (Boolean.parseBoolean(properties.getProperty("master.registry.recursive"))){
            url.append("&recursive={recursive}");//设置是否进行级联操作
            list.add(Boolean.parseBoolean(properties.getProperty("master.registry.recursive")));
        }

        map.put("url",url.toString());
        map.put("parames",list.toArray());
        return map;
    }

    /*//构造请求传递的参数
    private Object[] getParameValues(){
        RegistryEntity entity = new RegistryEntity();
        entity.setServiceIP(properties.getProperty("master.registry.service.ip"));
        entity.setServicePort(properties.getProperty("master.registry.service.port"));
        entity.setServiceName(properties.getProperty("master.registry.service.name"));
        Object[] parames = new Object[4];
        parames[0] = JSONObject.toJSONString(entity);
        parames[1] = Integer.parseInt(properties.getProperty("master.registry.ttl"));
        parames[2] = Boolean.parseBoolean(properties.getProperty("master.registry.dir"));
        parames[3] = Boolean.parseBoolean(properties.getProperty("master.registry.recursive"));
        return parames;
    }*/

    //打印获取的结果
    private void printResult(String type, KeyEntity entity){
        System.out.println(type + ":" + entity.getAciton() + "," + entity.getNode().getKey()
        + entity.getNode().getValue());
    }

    //注册Master service的线程，后端执行
    class RegistryMasterServiceThread implements Runnable {
        //打印日志
        Log log = LogFactory.getLog(RegistryMasterServiceThread.class);

        //请求的URL
        private String url;
        //请求的传递的参数
        private Object[] parames;

        //构造方法为请求URL与请求参数赋值
        public RegistryMasterServiceThread(String url, Object[] parames){
            this.url = url;
            this.parames = parames;
        }

        //运行线程
        public void run() {
            //计数
            int count = 0;
            while (true){
                try {

                    //注册Master service的信息
                    KeyEntity entity = masterDao.setKNodeInfos(url,parames);
                    //打印注册信息返回结果
                    printResult("Registry",entity);
//                    log.info("Registry: " + JSONObject.toJSONString(entity));
                    count ++;
                    if (count > 20){
                        break;
                    }
                    Thread.sleep(1000 * 60);
                } catch (Exception e) {
                    log.error("SetKeysThread error",e);
                }
            }
        }
    }

    //监控worker service的线程，后端执行
    class WatcherWorkerServiceThread implements Runnable{

        //打印日志
        Log log = LogFactory.getLog(WatcherWorkerServiceThread.class);

        //请求的URL
        private String url;

        //构造方法,为URL和请求参数赋值
        public WatcherWorkerServiceThread(String url){
            this.url = url;
        }

        //运行线程的方式
        public void run() {
            try {
                while (true){
                    //监控worker service的信息
                    KeyEntity entity = masterDao.watcherNodeInfos(url);

                    //打印监控信息
                    printResult("Watcher",entity);
//                    if (entity.getNode() != null){
//                        //获取worker service的信息
//                        String registry = entity.getNode().getValue();
//                        RegistryEntity registryEntity = JSONObject.parseObject(registry,RegistryEntity.class);
//                        log.info("Watcher: " + JSONObject.toJSONString(registryEntity));
//                    }
                    Thread.sleep(1000 * 60);
                }
            }catch (Exception e){
                log.error("WatcherKeys error",e);
            }
        }
    }

    //删除Master service的线程，后端执行
    class DeleteMasterServiceThread implements Runnable{

        Log log = LogFactory.getLog(DeleteMasterServiceThread.class);

        //请求的URL
        private String url;


        public DeleteMasterServiceThread(String url){
            this.url = url;
        }
        //运行删除Master service的线程
        public void run() {
            try {
                int count = 0;
                while (true){
                    //删除Master service
                    KeyEntity entity = masterDao.deleteMasterService(url);
                    //打印删除的信息
                    printResult("Delete",entity);
//                    if (entity.getNode() != null){//判断是否删除成功,并输出删除成功的信息
//                        String service = entity.getNode().getValue();
//                        RegistryEntity registryEntity = JSONObject.parseObject(service,RegistryEntity.class);
//                        log.info("Delete," + JSONObject.toJSONString(registryEntity));
//                    }
                    count ++;
                    if (count > 20){
                        break;
                    }
                    Thread.sleep(1000 * 60);
                }
            }catch (Exception e){
                log.error("MasterServiceImpl,DeleteMasterServiceThread,delete master service error");
            }
        }
    }
}
