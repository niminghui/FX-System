package com.shiep.fxauth.service.impl;

import com.shiep.fxauth.endpoint.IBankCardService;
import com.shiep.fxauth.endpoint.ICurrencyService;
import com.shiep.fxauth.endpoint.ITransactionService;
import com.shiep.fxauth.model.FxBankCard;
import com.shiep.fxauth.model.FxCurrency;
import com.shiep.fxauth.model.FxTransactionRecord;
import com.shiep.fxauth.service.IDepositInterestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/5/2 9:38
 * @description: 定时自动计算所有银行卡外汇的利息
 */
@Service
public class DepositInterestServiceImpl implements IDepositInterestService {

    private static final Logger logger = LoggerFactory.getLogger(DepositInterestServiceImpl.class);

    @Autowired
    @SuppressWarnings("all")
    private IBankCardService bankCardService;

    @Autowired
    private ICurrencyService currencyService;

    @Autowired
    private ITransactionService transactionService;

    @Override
    public void calculateCurrentInterest() {
        List<FxBankCard> bankCardList = bankCardService.findAll();
        List<FxCurrency> currencyList = currencyService.getAll();
        for (FxBankCard bankCard : bankCardList) {
            for (FxCurrency currency : currencyList) {
                FxTransactionRecord record = transactionService.calculateCurrentInterest(bankCard.getId(), currency.getEnglishName());
                if (record != null) {
                    logger.info(record.toString());
                }
            }
        }
    }
}
