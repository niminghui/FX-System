package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.vo.FxAccountVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: 倪明辉
 * @date: 2019/3/21 13:57
 * @description:
 */
// 指定服务ID（Service ID）
@FeignClient("fx-account")
public interface IAccountService {
    /**
     * description: 通过账户名得到账户视图
     *
     * @param accountName
     * @return com.shiep.fxauth.vo.FxAccountVo
     */
    @GetMapping("/account/{accountName}")
    FxAccountVo getAccountVo(@PathVariable("accountName") String accountName);

    /**
     * description: 登录界面
     *
     * @param
     * @return java.lang.String
     */
    @GetMapping("/login")
    String toLoginPage();
}
