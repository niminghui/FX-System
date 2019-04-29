package com.shiep.fxbankcard.repository;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * @author: 倪明辉
 * @date: 2019/4/25 16:09
 * @description: 测试交易记录分页功能s
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FxTransactionRecordRepositoryTest {
    @Autowired
    private FxTransactionRecordRepository transactionRecordRepository;

    @Test
    public void findByBankcardId() {
        Pageable pageable = new PageRequest(1, 5);
        Page<FxTransactionRecord> transactionRecords = transactionRecordRepository.findByBankcardId("6216602900057522721", pageable);
        System.out.println("总记录数:" + transactionRecords.getTotalElements());
        System.out.println("当前第几页: " + (transactionRecords.getNumber() + 1));
        System.out.println("总页数: " + transactionRecords.getTotalPages());
        System.out.println("当前页面的 List: " + transactionRecords.getContent());
        System.out.println("当前页面的记录数: " + transactionRecords.getNumberOfElements());
        System.out.println("当前的user对象的结果如下:");
        for (FxTransactionRecord user : transactionRecords.getContent()) {
            System.out.println(user.getCurrencyCode());
        }

    }

    @Test
    public void getTheMostRecentDepositTime() {
        System.out.println(transactionRecordRepository.getTheMostRecentDepositTime("6216602900057522721", "CNY"));
    }

    @Test
    public void findByBankcardIdAndCurrencyCodeOrderByTransactionTime() {
        System.out.println(transactionRecordRepository.findByBankcardIdAndCurrencyCodeOrderByTransactionTimeDesc("6216602900057522721", "CNY"));
    }

    @Test
    public void findByBankcardIdAndCurrencyCodeAndTransactionTimeAfterOrderByTransactionTimeDesc() {
        System.out.println(transactionRecordRepository.findByBankcardIdAndCurrencyCodeAndTransactionTimeAfterOrderByTransactionTimeDesc("6216602900057522721", "CNY", new Timestamp(System.currentTimeMillis())));
    }
}