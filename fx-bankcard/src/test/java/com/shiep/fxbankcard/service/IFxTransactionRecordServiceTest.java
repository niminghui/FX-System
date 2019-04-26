package com.shiep.fxbankcard.service;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author: 倪明辉
 * @date: 2019/4/25 17:50
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IFxTransactionRecordServiceTest {
    @Autowired
    private IFxTransactionRecordService transactionRecordService;

    @Test
    public void query() {
        Pageable pageable = new PageRequest(1, 5);
        Page<FxTransactionRecord> transactionRecords = transactionRecordService.query("6216602900057522721", null, null, null, null, pageable);
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
}