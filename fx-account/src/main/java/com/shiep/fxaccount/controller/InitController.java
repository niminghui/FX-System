package com.shiep.fxaccount.controller;

import com.shiep.fxaccount.service.IFxAccountService;
import com.shiep.fxaccount.vo.FxAccountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author: 倪明辉
 * @date: 2019/3/18 16:52
 * @description: 初始化Account数据
 */
@RestController
@RequestMapping("/admin")
public class InitController {
    private Logger logger = LoggerFactory.getLogger(InitController.class);

    @Autowired
    IFxAccountService accountService;

    @GetMapping("/initAdmin")
    public FxAccountVo initAdminAccount(){
        logger.info("create admin account 20150600");
        return accountService.createAndAuth("20150600","a20150600", Arrays.asList("ROLE_ADMIN","ROLE_DBA","ROLE_VISITOR"));
    }

    @GetMapping("/initVisitor")
    public FxAccountVo initVisitorAccount(){
        logger.info("create visitor account 20150000");
        return accountService.create("20150000","a20150000");
    }
}
