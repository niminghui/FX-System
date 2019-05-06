package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import com.shiep.fxbankcard.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author: 倪明辉
 * @date: 2019/4/30 16:28
 * @description: 交易控制层
 */
@RestController
@RequestMapping(path = "/transaction", produces = "application/json;charset=utf-8")
public class TransactionController {
    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/calculate/interest")
    public FxTransactionRecord calculateCurrentInterest(@RequestParam("bankcardID") String bankcardID,
                                                        @RequestParam("currencyCode") String currencyCode) {
        return transactionService.calculateCurrentInterest(bankcardID, currencyCode);
    }

    @PostMapping("/deposit")
    public FxTransactionRecord deposit(@RequestParam("bankcardID") String bankcardID,
                                       @RequestParam("currencyCode") String currencyCode,
                                       @RequestParam("money") BigDecimal money) {
        return transactionService.deposit(bankcardID, currencyCode, money);
    }

    @PostMapping("/transfer")
    public FxTransactionRecord transfer(@RequestParam("bankcardID") String bankcardID,
                                        @RequestParam("currencyCode") String currencyCode,
                                        @RequestParam("money") BigDecimal money,
                                        @RequestParam("otherBankcardID") String otherBankcardID) {
        return transactionService.transfer(bankcardID, currencyCode, money, otherBankcardID);
    }

    @PostMapping("/fx/trading")
    public Boolean foreignExchangeTrading(@RequestParam("bankcardID") String bankcardID,
                                          @RequestParam("basicCurrency") String basicCurrency,
                                          @RequestParam("secondaryCurrency") String secondaryCurrency,
                                          @RequestParam("money") BigDecimal money,
                                          @RequestParam("rate") BigDecimal rate,
                                          @RequestParam("buy") Boolean buy) {
        return transactionService.foreignExchangeTrading(bankcardID, basicCurrency, secondaryCurrency, money, rate, buy);
    }

    @PostMapping("/fx/settlement")
    public Boolean foreignExchangeSettlement(@RequestParam("bankcardID") String bankcardID,
                                             @RequestParam("currencyCode") String currencyCode,
                                             @RequestParam("money") BigDecimal money,
                                             @RequestParam("rate") BigDecimal rate) {
        return transactionService.foreignExchangeSettlement(bankcardID, currencyCode, money, rate);
    }

    @PostMapping("/fx/purchase")
    public Boolean foreignExchangePurchase(@RequestParam("bankcardID") String bankcardID,
                                           @RequestParam("currencyCode") String currencyCode,
                                           @RequestParam("money") BigDecimal money,
                                           @RequestParam("rate") BigDecimal rate) {
        return transactionService.foreignExchangePurchase(bankcardID, currencyCode, money, rate);
    }
}
