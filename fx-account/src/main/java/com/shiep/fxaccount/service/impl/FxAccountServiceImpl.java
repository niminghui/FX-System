package com.shiep.fxaccount.service.impl;

import com.shiep.fxaccount.entity.FxAccount;
import com.shiep.fxaccount.entity.FxAccountRole;
import com.shiep.fxaccount.repository.FxAccountRepository;
import com.shiep.fxaccount.repository.FxAccountRoleRepository;
import com.shiep.fxaccount.service.IFxAccountService;
import com.shiep.fxaccount.utils.UuidTools;
import com.shiep.fxaccount.vo.FxAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 11:17
 * @description: IFxAccountService的实现类
 */
@Service
public class FxAccountServiceImpl implements IFxAccountService {

    @Autowired
    FxAccountRepository accountRepository;

    @Autowired
    FxAccountRoleRepository accountRoleRepository;

    @Override
    public FxAccount find(String accountName) {
        return accountRepository.findByName(accountName);
    }

    @Override
    public List<String> getRoles(String accountName) {
        List<FxAccountRole> accountRoleList=accountRoleRepository.findByAccountName(accountName);
        if (accountRoleList==null){
            return null;
        }
        List<String> roles=new ArrayList<>();
        for(FxAccountRole accountRole : accountRoleList){
            roles.add(accountRole.getRoleName());
        }
        return roles;
    }

    @Override
    public FxAccountVo create(String accountName, String accountPwd) {
        FxAccount account = new FxAccount();
        account.setId(UuidTools.getUUID());
        account.setName(accountName);
        account.setPassword(accountPwd);
        FxAccount accountResult = accountRepository.save(account);
        if (accountResult==null){
            return null;
        }
        if (authorization(accountName,"ROLE_VISITOR")){
            return new FxAccountVo(account,"ROLE_VISITOR");
        }
        return null;
    }

    @Override
    public Boolean authorization(String accountName, String roleName) {
        FxAccountRole accountRole = new FxAccountRole();
        accountRole.setId(UuidTools.getUUID());
        accountRole.setAccountName(accountName);
        accountRole.setRoleName(roleName);
        accountRole.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        return accountRoleRepository.save(accountRole)!=null;
    }
}
