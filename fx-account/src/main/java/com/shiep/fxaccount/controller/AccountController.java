package com.shiep.fxaccount.controller;

import com.shiep.fxaccount.service.IFxAccountService;
import com.shiep.fxaccount.vo.FxAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 倪明辉
 * @date: 2019/3/21 14:03
 * @description: account控制层
 */
@RestController
@RequestMapping(path = "/account",produces = "application/json;charset=utf-8")
public class AccountController {
    @Autowired
    IFxAccountService accountService;

    @GetMapping("/{accountName}")
    public FxAccountVo getAccountVo(@PathVariable("accountName") String accountName){
        return accountService.getAccountVo(accountName);
    }
}
