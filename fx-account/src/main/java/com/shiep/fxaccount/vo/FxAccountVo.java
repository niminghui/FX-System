package com.shiep.fxaccount.vo;

import com.shiep.fxaccount.entity.FxAccount;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 11:29
 * @description: 账户视图类
 */
@Data
public class FxAccountVo {
    private String accountId;
    private String accountName;
    private String accountPwd;
    private String bankcardId;
    private List<String> roles;

    public FxAccountVo(FxAccount account,List<String> roles){
        this.accountId=account.getId();
        this.accountName=account.getName();
        this.accountPwd=account.getPassword();
        this.bankcardId=account.getBankCardId();
        this.roles=roles;
    }

    public FxAccountVo(FxAccount account,String role){
        this.accountId=account.getId();
        this.accountName=account.getName();
        this.accountPwd=account.getPassword();
        this.bankcardId=account.getBankCardId();
        this.roles=new ArrayList<>();
        this.roles.add(role);
    }
}
