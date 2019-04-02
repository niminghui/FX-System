package com.shiep.fxaccount.controller;

import com.shiep.fxaccount.service.IFxAccountService;
import com.shiep.fxaccount.vo.FxAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{accountName}/{password}")
    public FxAccountVo createAccount(@PathVariable("accountName") String accountName,
                                     @PathVariable("password") String password){
        return accountService.create(accountName,password);
    }

    @PostMapping
    public FxAccountVo createAccountAndAuth(@RequestBody FxAccountVo accountVo){
        return accountService.createAndAuth(accountVo.getAccountName(),accountVo.getAccountPwd(),accountVo.getRoles());
    }

    @PostMapping("/authorization/{accountName}/{roleName}")
    public Boolean authorization(@PathVariable("accountName")String accountName,
                                 @PathVariable("roleName")String roleName){
        return accountService.authorization(accountName, roleName);
    }

    @PutMapping("/{accountName}/{newPassword}")
    public Boolean updatePassword(@PathVariable("accountName")String accountName,
                                  @PathVariable("newPassword")String newPassword){
        return accountService.updatePassword(accountName, newPassword)!=null;
    }

    @PutMapping("/bindBankCard/{accountName}/{bankCard}")
    public Boolean bindBankCard(@PathVariable("accountName")String accountName,
                                @PathVariable("bankCard")String bankCard){
        return accountService.bindBankCard(accountName, bankCard)!=null;
    }
}
