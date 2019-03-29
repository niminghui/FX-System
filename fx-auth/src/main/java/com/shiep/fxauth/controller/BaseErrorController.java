package com.shiep.fxauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 倪明辉
 * @date: 2019/3/29 10:34
 * @description: 实现ErrorController，错误控制器
 */
@Controller
public class BaseErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(BaseErrorController.class);
    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(value = "/error", produces = "text/html")
    public ModelAndView handleError(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        String message = null;
        if(request.getAttribute("msg") == null){
            message = "发生了未知的错误";
        }else {
            message=request.getAttribute("msg").toString();
        }
        modelAndView.addObject("msg",message);
        modelAndView.setViewName("error");
        logger.warn("error message:"+message);
        return modelAndView;
    }
}