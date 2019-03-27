package com.shiep.fxauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: 倪明辉
 * @date: 2019/3/25 14:22
 * @description:
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String toHomePage(){
        return "homePage";
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    @ResponseBody
    public String toTest(){
        return redisTemplate.opsForValue().get("key1").toString();
    }
}
