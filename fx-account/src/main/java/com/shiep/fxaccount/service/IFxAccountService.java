package com.shiep.fxaccount.service;

import com.shiep.fxaccount.entity.FxAccount;
import com.shiep.fxaccount.vo.FxAccountVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 10:58
 * @description: FxAccount服务接口
 */
@Transactional(rollbackFor = Exception.class)
public interface IFxAccountService {
    /**
     * description: 通过账户名查找FxAccount
     *
     * @param accountName
     * @return com.shiep.fxaccount.entity.FxAccount
     */
    FxAccount find(String accountName);

    /**
     * description: 通过账户名查找该账户具有的角色列表
     *
     * @param accountName
     * @return java.util.List<java.lang.String>
     */
    List<String> getRoles(String accountName);

    /**
     * description: 通过账户名和密码构建账户（账户初始权限为visitor，bankcardId字段为空）
     *
     * @param accountName
     * @param accountPwd
     * @return com.shiep.fxaccount.entity.FxAccount
     */
    FxAccountVo create(String accountName, String accountPwd);

    /**
     * description: 赋予accountName账户roleName角色（授权）
     *
     * @param accountName
     * @param roleName
     * @return java.lang.Boolean
     */
    Boolean authorization(String accountName, String roleName);
}
