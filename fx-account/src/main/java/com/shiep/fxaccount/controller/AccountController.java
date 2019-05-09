package com.shiep.fxaccount.controller;

import com.shiep.fxaccount.service.IFxAccountService;
import com.shiep.fxaccount.vo.FxAccountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    IFxAccountService accountService;

    @GetMapping("/{accountName}")
    public FxAccountVo getAccountVo(@PathVariable("accountName") String accountName){
        logger.info("query account by "+accountName);
        return accountService.getAccountVo(accountName);
    }

    @PostMapping("/{accountName}/{password}")
    public FxAccountVo createAccount(@PathVariable("accountName") String accountName,
                                     @PathVariable("password") String password){
        logger.info("create account with accountName = "+accountName);
        return accountService.create(accountName,password);
    }

    @PostMapping
    public FxAccountVo createAccountAndAuth(@RequestBody FxAccountVo accountVo){
        logger.info("create account and auth with accountName = "+accountVo.getAccountName());
        return accountService.createAndAuth(accountVo.getAccountName(),accountVo.getAccountPwd(),accountVo.getRoles());
    }

    @PostMapping("/authorization/{accountName}/{roleName}")
    public Boolean authorization(@PathVariable("accountName")String accountName,
                                 @PathVariable("roleName")String roleName){
        logger.info("authorization "+accountName+" with role : "+roleName);
        return accountService.authorization(accountName, roleName);
    }

    @PutMapping("/{accountName}/{newPassword}")
    public Boolean updatePassword(@PathVariable("accountName")String accountName,
                                  @PathVariable("newPassword")String newPassword){
        logger.info("update password with accountName = "+accountName);
        return accountService.updatePassword(accountName, newPassword)!=null;
    }

    @PutMapping("/bindBankCard/{accountName}/{bankCard}")
    public Boolean bindBankCard(@PathVariable("accountName")String accountName,
                                @PathVariable("bankCard")String bankCard){
        logger.info(accountName+" bind bankcard and bankcard id = "+bankCard);
        return accountService.bindBankCard(accountName, bankCard)!=null;
    }
}
