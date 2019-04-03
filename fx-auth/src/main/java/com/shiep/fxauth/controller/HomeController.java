package com.shiep.fxauth.controller;

import com.shiep.fxauth.endpoint.IApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/25 14:22
 * @description: 用户主页控制器
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String toHomePage(){
        return "homePage";
    }

    @GetMapping("/test")
    @ResponseBody
    public String toTest(){
        return "redirect:/a/account/20150600";
    }

    @Autowired
    private IApiService apiService;

    @GetMapping("/api")
    @ResponseBody
    public Map<String, Object> getFxRate(){
        return apiService.getFxRate();
    }
}
