package com.shiep.fxauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
