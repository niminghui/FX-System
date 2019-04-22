package com.shiep.fxauth.controller;

import com.shiep.fxauth.endpoint.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: 倪明辉
 * @date: 2019/4/22 16:24
 * @description: 存款业务控制层
 */
@RestController
@RequestMapping("/deposit")
public class DepositController {
    @Autowired
    private ICurrencyService currencyService;

    @GetMapping("/rate")
    public ModelAndView showDepositRate() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("rate", currencyService.getAll());
        mv.setViewName("depositRate");
        return mv;
    }
}
