package com.shiep.fxaccount.service.impl;

import com.shiep.fxaccount.entity.FxAccount;
import com.shiep.fxaccount.entity.FxAccountRole;
import com.shiep.fxaccount.repository.FxAccountRepository;
import com.shiep.fxaccount.repository.FxAccountRoleRepository;
import com.shiep.fxaccount.service.IFxAccountService;
import com.shiep.fxaccount.utils.UuidTools;
import com.shiep.fxaccount.vo.FxAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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
    public FxAccountVo getAccountVo(String accountName) {
        return new FxAccountVo(find(accountName),getRoles(accountName));
    }

    @Override
    public FxAccountVo create(String accountName, String accountPwd) {
        FxAccount account = createAccount(accountName,accountPwd,null);
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

    @Override
    public FxAccountVo createAndAuth(String accountName, String accountPwd, List<String> roles) {
        FxAccount account = createAccount(accountName,accountPwd,null);
        List<String> resultRoles = new ArrayList<>();
        for (String roleName : roles){
            if (authorization(accountName,roleName)){
                resultRoles.add(roleName);
            }
        }
        return new FxAccountVo(account,resultRoles);
    }

    @Override
    public FxAccount updatePassword(String accountName,  String newPassword) {
        FxAccount account = accountRepository.findByName(accountName);
        if (account==null)
            return null;
        account.setPassword(passwordEncoder.encode(newPassword));
        return accountRepository.save(account);
    }

    @Override
    public FxAccount bindBankCard(String accountName, String bankCard) {
        FxAccount account = accountRepository.findByName(accountName);
        if(account==null)
            return null;
        account.setBankCardId(bankCard);
        return accountRepository.save(account);
    }

    /**
     * description: 创建账户
     *
     * @param accountName 账户名
     * @param accountPwd 密码
     * @param bankcardID 银行卡号
     * @return com.shiep.fxaccount.entity.FxAccount
     */
    private FxAccount createAccount(String accountName, String accountPwd, String bankcardID){
        FxAccount account = new FxAccount();
        account.setId(UuidTools.getUUID());
        account.setName(accountName);
        // 密码加密后存入数据库
        account.setPassword(passwordEncoder.encode(accountPwd));
        account.setBankCardId(bankcardID);
        return accountRepository.save(account);
    }
}
