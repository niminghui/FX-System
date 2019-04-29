package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.service.ITransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}