package io.xlauncher.utils;

import org.springframework.web.servlet.ModelAndView;

public class ExceptionUtils extends Exception{

    //承接异常信息
    private Exception e;

    public ExceptionUtils(Exception e){
        this.e = e;
        turnErrorPage();
    }

    public ModelAndView turnErrorPage(){
        return new ModelAndView("error");
    }

}
