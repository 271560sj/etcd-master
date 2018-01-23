package io.xlauncher.controller;

import io.xlauncher.service.MasterService;
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

    @Autowired
    MasterService masterService;

    /**
     * 启动Master service
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/masterService")
    public void masterService()throws Exception{
        //注册Master service
        masterService.registryMasterService();

        //删除Master service注册的信息
        masterService.deleteMasterService();

        //监控worker service
        masterService.watcherKWorkerService();
    }

    @ResponseBody
    @RequestMapping(value = "/getIndex")
    public ModelAndView getIndex()throws Exception{
        return new ModelAndView("index");
    }
}
