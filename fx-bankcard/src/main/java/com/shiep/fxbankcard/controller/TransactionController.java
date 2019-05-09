package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import com.shiep.fxbankcard.service.ITransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/calculate/interest")
    public FxTransactionRecord calculateCurrentInterest(@RequestParam("bankcardID") String bankcardID,
                                                        @RequestParam("currencyCode") String currencyCode) {
        logger.info("calculate current interest");
        FxTransactionRecord record = transactionService.calculateCurrentInterest(bankcardID, currencyCode);
        logger.info(record.toString());
        return record;
    }

    @PostMapping("/deposit")
    public FxTransactionRecord deposit(@RequestParam("bankcardID") String bankcardID,
                                       @RequestParam("currencyCode") String currencyCode,
                                       @RequestParam("money") BigDecimal money) {
        logger.info("begin deposit");
        FxTransactionRecord record = transactionService.deposit(bankcardID, currencyCode, money);
        logger.info(record.toString());
        return record;
    }

    @PostMapping("/transfer")
    public FxTransactionRecord transfer(@RequestParam("bankcardID") String bankcardID,
                                        @RequestParam("currencyCode") String currencyCode,
                                        @RequestParam("money") BigDecimal money,
                                        @RequestParam("otherBankcardID") String otherBankcardID) {
        logger.info("begin transfer");
        FxTransactionRecord record = transactionService.transfer(bankcardID, currencyCode, money, otherBankcardID);
        logger.info(record.toString());
        return record;
    }

    @PostMapping("/fx/trading")
    public Boolean foreignExchangeTrading(@RequestParam("bankcardID") String bankcardID,
                                          @RequestParam("basicCurrency") String basicCurrency,
                                          @RequestParam("secondaryCurrency") String secondaryCurrency,
                                          @RequestParam("money") BigDecimal money,
                                          @RequestParam("rate") BigDecimal rate,
                                          @RequestParam("buy") Boolean buy) {
        Boolean result = transactionService.foreignExchangeTrading(bankcardID, basicCurrency, secondaryCurrency, money, rate, buy);
        logger.info("foreign exchange trading : " + result);
        return result;
    }

    @PostMapping("/fx/settlement")
    public Boolean foreignExchangeSettlement(@RequestParam("bankcardID") String bankcardID,
                                             @RequestParam("currencyCode") String currencyCode,
                                             @RequestParam("money") BigDecimal money,
                                             @RequestParam("rate") BigDecimal rate) {
        Boolean result = transactionService.foreignExchangeSettlement(bankcardID, currencyCode, money, rate);
        logger.info("foreign exchange settlement : " + result);
        return result;
    }

    @PostMapping("/fx/purchase")
    public Boolean foreignExchangePurchase(@RequestParam("bankcardID") String bankcardID,
                                           @RequestParam("currencyCode") String currencyCode,
                                           @RequestParam("money") BigDecimal money,
                                           @RequestParam("rate") BigDecimal rate) {
        Boolean result = transactionService.foreignExchangePurchase(bankcardID, currencyCode, money, rate);
        logger.info("foreign exchange purchase : " + result);
        return result;
    }
}
