package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.service.ITransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author: 倪明辉
 * @date: 2019/4/29 17:06
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceImplTest {

    @Autowired
    private ITransactionService transactionService;

    @Test
    public void calculateCurrentInterest() {
        System.out.println(transactionService.calculateCurrentInterest("6216602900057522721", "CNY"));
    }

    @Test
    public void deposit() {
        System.out.println(transactionService.deposit("6216602900057522721", "AUD", new BigDecimal("20000")));
    }

    @Test
    public void transfer() {
        System.out.println(transactionService.transfer("6216602900057522721", "AUD", new BigDecimal("2000"), "6216602900730175428"));
    }

    @Test
    public void foreignExchangeTrading() {
        System.out.println(transactionService.foreignExchangeTrading("6216602900057522721", "USD",
                "CNY", new BigDecimal("100"), new BigDecimal("6.7378"), true));
    }

    @Test
    public void foreignExchangeSettlement() {
        assertEquals(true, transactionService.foreignExchangeSettlement("6216602900057522721",
                "AUD", new BigDecimal("2000"), new BigDecimal("472.9200")));
    }

    @Test
    public void foreignExchangePurchase() {
        assertEquals(true, transactionService.foreignExchangePurchase("6216602900057522721",
                "AUD", new BigDecimal("1000"), new BigDecimal("476.4000")));
    }
}