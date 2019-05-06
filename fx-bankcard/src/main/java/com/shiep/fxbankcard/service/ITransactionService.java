package com.shiep.fxbankcard.service;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author: 倪明辉
 * @date: 2019/4/29 11:09
 * @description: 交易服务层
 */
@Transactional(rollbackFor = Exception.class)
public interface ITransactionService {

    /**
     * description: 计算活期利息
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 币种
     * @return com.shiep.fxbankcard.entity.FxTransactionRecord
     */
    FxTransactionRecord calculateCurrentInterest(String bankcardID, String currencyCode);

    /**
     * description: 外汇存款
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 币种
     * @param money        金额
     * @return com.shiep.fxbankcard.entity.FxTransactionRecord
     */
    FxTransactionRecord deposit(String bankcardID, String currencyCode, BigDecimal money);

    /**
     * description: 外汇转账
     *
     * @param bankcardID      银行卡号码
     * @param currencyCode    币种
     * @param money           金额
     * @param otherBankcardID 交易方
     * @return com.shiep.fxbankcard.entity.FxTransactionRecord
     */
    FxTransactionRecord transfer(String bankcardID, String currencyCode, BigDecimal money, String otherBankcardID);

    /**
     * description: 外汇买卖（外币之间的交易）
     *
     * @param bankcardID        银行卡号码
     * @param basicCurrency     基本货币
     * @param secondaryCurrency 二级货币
     * @param money             买卖金额
     * @param rate              汇率(交易单位：1)
     * @param buy               true为买入，false为卖出
     * @return java.lang.Boolean
     */
    Boolean foreignExchangeTrading(String bankcardID, String basicCurrency, String secondaryCurrency, BigDecimal money, BigDecimal rate, Boolean buy);

    /**
     * description: 人民币结汇（外汇换人民币）
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 外汇币种
     * @param money        金额
     * @param rate         汇率（交易单位：100）
     * @return java.lang.Boolean
     */
    Boolean foreignExchangeSettlement(String bankcardID, String currencyCode, BigDecimal money, BigDecimal rate);

    /**
     * description: 人民币购汇（人民币购买外汇）
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 外汇币种
     * @param money        金额
     * @param rate         汇率（交易单位：100）
     * @return java.lang.Boolean
     */
    Boolean foreignExchangePurchase(String bankcardID, String currencyCode, BigDecimal money, BigDecimal rate);
}
