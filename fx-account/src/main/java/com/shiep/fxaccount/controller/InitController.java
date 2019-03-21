package com.shiep.fxaccount.controller;

import com.shiep.fxaccount.service.IFxAccountService;
import com.shiep.fxaccount.vo.FxAccountVo;
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
    @Autowired
    IFxAccountService accountService;

    @GetMapping("/initAdmin")
    public FxAccountVo initAdminAccount(){
        return accountService.createAndAuth("20150600","20150600", Arrays.asList("ROLE_ADMIN","ROLE_DBA","ROLE_VISITOR"));
    }

    @GetMapping("/initVisitor")
    public FxAccountVo initVisitorAccount(){
        return accountService.create("20150000","20150000");
    }
}
