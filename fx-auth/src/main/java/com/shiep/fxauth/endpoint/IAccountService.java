package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.vo.FxAccountVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author: 倪明辉
 * @date: 2019/3/21 13:57
 * @description: fx-account服务端点
 */
@FeignClient("fx-account")
public interface IAccountService {
    /**
     * description: 通过账户名得到账户视图
     *
     * @param accountName 账户名
     * @return com.shiep.fxauth.vo.FxAccountVo
     */
    @GetMapping("/account/{accountName}")
    FxAccountVo getAccountVo(@PathVariable("accountName") String accountName);

    /**
     * description: 通过账户名和密码构建账户（账户初始权限为visitor，bankcardId字段为空）
     *
     * @param accountName 账户名
     * @param password    密码
     * @return com.shiep.fxaccount.entity.FxAccount
     */
    @PostMapping("/account/{accountName}/{password}")
    FxAccountVo createAccount(@PathVariable("accountName") String accountName, @PathVariable("password") String password);

    /**
     * description: 创建账户并授权
     *
     * @param accountVo 账户视图对象
     * @return com.shiep.fxauth.vo.FxAccountVo
     */
    @PostMapping("/account")
    FxAccountVo createAccountAndAuth(FxAccountVo accountVo);

    /**
     * description: 赋予accountName账户roleName角色（授权）
     *
     * @param accountName 账户名
     * @param roleName    角色名
     * @return java.lang.Boolean
     */
    @PostMapping("/account/authorization/{accountName}/{roleName}")
    Boolean authorization(@PathVariable("accountName")String accountName, @PathVariable("roleName")String roleName);

    /**
     * description: 更改密码
     *
     * @param accountName 账户名
     * @param newPassword 新密码
     * @return com.shiep.fxaccount.vo.FxAccountVo 账户视图对象
     */
    @PutMapping("/account/{accountName}/{newPassword}")
    Boolean updatePassword(@PathVariable("accountName")String accountName, @PathVariable("newPassword")String newPassword);

    /**
     * description: 绑定银行卡
     *
     * @param accountName 账户名
     * @param bankCard 银行卡号
     * @return com.shiep.fxaccount.entity.FxAccount
     */
    @PutMapping("/account/bindBankCard/{accountName}/{bankCard}")
    Boolean bindBankCard(@PathVariable("accountName")String accountName, @PathVariable("bankCard")String bankCard);
}
