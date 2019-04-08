package com.shiep.fxauth.controller;

import com.shiep.fxauth.endpoint.IApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/25 14:22
 * @description: 用户主页控制器
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @SuppressWarnings("all")
    @Autowired
    private IApiService apiService;

    @GetMapping
    public ModelAndView toHomePage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("fxRate", apiService.getFxRate());
        mv.addObject("rmbRate", apiService.getRmbRate());
        mv.addObject("headlines", apiService.getHeadlinesPageable("guoji", 3));
        mv.setViewName("homePage");
        return mv;
    }

    //    @GetMapping("/test")
//    @ResponseBody
//    public String toTest(){
//        return "redirect:/a/account/20150600";
//    }
//
    @GetMapping("/api")
    @ResponseBody
    public Map<String, Object> getFxRate(){
        return apiService.getHeadlinesPageable("guoji", 3);
    }
}
