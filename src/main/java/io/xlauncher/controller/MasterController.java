package io.xlauncher.controller;

import io.xlauncher.entity.KeyEntity;
import io.xlauncher.service.MasterService;
import io.xlauncher.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * controller,用于控制master的业务
 */
@Controller
@RequestMapping(value = "/etcd")
public class MasterController {

    //获取MasterService实例
    @Autowired
    private MasterService masterService;

    /**
     * 启动Master service，该方式没有向前端返回信息
     * 所有信息会在后端线程执行并打印
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/masterService")
    public void masterService() throws ExceptionUtils {
        try {
            //注册Master service
            masterService.registryMasterService();

            //删除Master service注册的信息
            masterService.deleteMasterService();

            //监控worker service
            masterService.watcherKWorkerService();
        }catch (Exception e){
            throw new ExceptionUtils(e);
        }
    }

    /**
     * 通过前端的ajax请求注册服务信息
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/registerService")
    public KeyEntity registryMasterService()throws ExceptionUtils{
        try {
            KeyEntity entity = masterService.registryMasterServices();
            return entity;
        }catch (Exception e){
            throw new ExceptionUtils(e);
        }
    }

    /**
     * 通过前端的ajax请求删除Master service
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/deleteService")
    public KeyEntity deleteMasterService()throws ExceptionUtils{
        try {
            KeyEntity entity = masterService.deleteMasterServices();
            return entity;
        }catch (Exception e){
            throw new ExceptionUtils(e);
        }
    }

    /**
     * 监控worker service的服务信息，通过前端ajax进行请求
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/watcherService")
    public KeyEntity watcherMasterService()throws ExceptionUtils{
        try {
            KeyEntity entity = masterService.watcherKWorkerServices();
            return entity;
        }catch (Exception e){
            throw new ExceptionUtils(e);
        }
    }

    /**
     * 进入主页
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getIndex")
    public ModelAndView getIndex()throws ExceptionUtils{
        try {
            return new ModelAndView("index");
        }catch (Exception e){
            throw new ExceptionUtils(e);
        }
    }
}
