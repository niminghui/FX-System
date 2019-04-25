package com.shiep.fxauth.controller;

import com.shiep.fxauth.common.HeadlinesTypeEnum;
import com.shiep.fxauth.endpoint.IApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: 倪明辉
 * @date: 2019/4/25 10:18
 * @description: 外汇兑换业务控制层
 */
@Controller
@RequestMapping("/fx")
public class ForeignExchangeController {
    @Autowired
    private IApiService apiService;

    @GetMapping("/headlines")
    public ModelAndView showHeadlines() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("types", HeadlinesTypeEnum.values());
        // 默认是国际新闻
        mv.addObject("headlines", apiService.getHeadlinesPageable("guoji", 3));
        mv.setViewName("headlines");
        return mv;
    }

    @GetMapping("/headlines/{type}")
    public ModelAndView showHeadlinesWithType(@PathVariable("type") String type) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("types", HeadlinesTypeEnum.values());
        // 默认是国际新闻
        mv.addObject("headlines", apiService.getHeadlinesPageable(type, 3));
        mv.setViewName("headlines");
        return mv;
    }
}
