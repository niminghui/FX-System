package com.shiep.fxaccount.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
    @HystrixCommand(fallbackMethod = "getAccountVoError",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public FxAccountVo getAccountVo(@PathVariable("accountName") String accountName){
        logger.info("query account by "+accountName);
        return accountService.getAccountVo(accountName);
    }

    /**
     * description: getAccountVo的请求超时回调方法
     *
     * @param accountName 账户名
     * @return com.shiep.fxaccount.vo.FxAccountVo
     */
    public FxAccountVo getAccountVoError(String accountName){
        logger.warn("query account "+accountName+" timeout. Use a circuit breaker.");
        return null;
    }

    @PostMapping("/{accountName}/{password}")
    @HystrixCommand(fallbackMethod = "createAccountError",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public FxAccountVo createAccount(@PathVariable("accountName") String accountName,
                                     @PathVariable("password") String password){
        logger.info("create account with accountName = "+accountName);
        return accountService.create(accountName,password);
    }

    /**
     * description: createAccount的请求超时回调方法
     *
     * @param accountName 账户名
     * @param password 密码
     * @return com.shiep.fxaccount.vo.FxAccountVo
     */
    public FxAccountVo createAccountError(String accountName, String password){
        logger.warn("create account "+accountName+" timeout");
        return null;
    }

    @PostMapping
    @HystrixCommand(fallbackMethod = "createAccountAndAuthError",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public FxAccountVo createAccountAndAuth(@RequestBody FxAccountVo accountVo){
        logger.info("create account and auth with accountName = "+accountVo.getAccountName());
        return accountService.createAndAuth(accountVo.getAccountName(),accountVo.getAccountPwd(),accountVo.getRoles());
    }

    /**
     * description: 熔断器，降级服务
     *
     * @param accountVo 账户视图对象
     * @return com.shiep.fxaccount.vo.FxAccountVo
     */
    public FxAccountVo createAccountAndAuthError(FxAccountVo accountVo){
        logger.warn("create account and authorization time");
        return null;
    }

    @PostMapping("/authorization/{accountName}/{roleName}")
    @HystrixCommand(fallbackMethod = "authorizationError",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Boolean authorization(@PathVariable("accountName")String accountName,
                                 @PathVariable("roleName")String roleName){
        logger.info("authorization "+accountName+" with role : "+roleName);
        return accountService.authorization(accountName, roleName);
    }

    /**
     * description: 熔断器，降级服务
     *
     * @param accountName 账户名
     * @param roleName 角色名
     * @return java.lang.Boolean
     */
    public Boolean authorizationError(String accountName, String roleName){
        logger.warn("authorization timeout");
        return null;
    }

    @PutMapping("/{accountName}/{newPassword}")
    @HystrixCommand(fallbackMethod = "updatePasswordError",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Boolean updatePassword(@PathVariable("accountName")String accountName,
                                  @PathVariable("newPassword")String newPassword){
        logger.info("update password with accountName = "+accountName);
        return accountService.updatePassword(accountName, newPassword)!=null;
    }

    /**
     * description: 熔断器，降级服务
     *
     * @param accountName 账户名
     * @param newPassword 新密码
     * @return java.lang.Boolean
     */
    public Boolean updatePasswordError(String accountName,String newPassword){
        logger.warn(accountName+" update password timeout");
        return null;
    }

    @PutMapping("/bindBankCard/{accountName}/{bankCard}")
    @HystrixCommand(fallbackMethod = "bindBankCardError",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Boolean bindBankCard(@PathVariable("accountName")String accountName,
                                @PathVariable("bankCard")String bankCard){
        logger.info(accountName+" bind bankcard and bankcard id = "+bankCard);
        return accountService.bindBankCard(accountName, bankCard)!=null;
    }

    /**
     * description: 熔断器，降级服务
     *
     * @param accountName 账户名
     * @param bankCard 银行卡号码
     * @return java.lang.Boolean
     */
    public Boolean bindBankCardError(String accountName,String bankCard){
        logger.warn(accountName+" bind bankcard "+bankCard+" timeout");
        return null;
    }
}
