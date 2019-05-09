package com.shiep.fxaccount.repository;

import com.shiep.fxaccount.entity.FxAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 10:39
 * @description: FxAccount的dao层
 */
public interface FxAccountRepository extends JpaRepository<FxAccount,String> {
    /**
     * description: 通过账户名查找FxAccount
     *
     * @param accountName 账户名
     * @return com.shiep.fxaccount.entity.FxAccount
     */
    FxAccount findByName(String accountName);

    /**
     * description: 通过银行卡号查找FxAccount
     *
     * @param bankCardId 银行卡号
     * @return com.shiep.fxaccount.entity.FxAccount
     */
    FxAccount findByBankCardId(String bankCardId);
}
