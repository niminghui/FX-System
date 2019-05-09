package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import com.shiep.fxbankcard.model.TransactionRecordPage;
import com.shiep.fxbankcard.service.IFxTransactionRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * @author: 倪明辉
 * @date: 2019/4/23 15:46
 * @description: 交易记录控制层
 */
@RestController
@RequestMapping(path = "/record", produces = "application/json;charset=utf-8")
public class FxTransactionRecordController {
    private Logger logger = LoggerFactory.getLogger(FxTransactionRecordController.class);

    @Autowired
    private IFxTransactionRecordService transactionRecordService;

    @GetMapping("/type")
    public TransactionRecordPage findByType(@RequestParam("type") Integer type,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("size") Integer size) {
        logger.info("query transaction");
        Pageable pageable = new PageRequest(page - 1, size);
        Page<FxTransactionRecord> transactionRecords = transactionRecordService.findByType(type, pageable);
        TransactionRecordPage transactionRecordPage = new TransactionRecordPage();
        transactionRecordPage.setSize(transactionRecords.getTotalElements());
        transactionRecordPage.setPageIndex(transactionRecords.getNumber() + 1);
        transactionRecordPage.setPageSize(transactionRecords.getTotalPages());
        transactionRecordPage.setPageContent(transactionRecords.getContent());
        transactionRecordPage.setCurrencyPageSize(transactionRecords.getNumberOfElements());
        return transactionRecordPage;
    }

    @GetMapping("/currency")
    public TransactionRecordPage findByCurrency(@RequestParam("currencyCode") String currencyCode,
                                                @RequestParam("page") Integer page,
                                                @RequestParam("size") Integer size) {
        logger.info("query transaction");
        Pageable pageable = new PageRequest(page - 1, size);
        Page<FxTransactionRecord> transactionRecords = transactionRecordService.findByCurrency(currencyCode, pageable);
        TransactionRecordPage transactionRecordPage = new TransactionRecordPage();
        transactionRecordPage.setSize(transactionRecords.getTotalElements());
        transactionRecordPage.setPageIndex(transactionRecords.getNumber() + 1);
        transactionRecordPage.setPageSize(transactionRecords.getTotalPages());
        transactionRecordPage.setPageContent(transactionRecords.getContent());
        transactionRecordPage.setCurrencyPageSize(transactionRecords.getNumberOfElements());
        return transactionRecordPage;
    }

    @GetMapping("/time")
    public TransactionRecordPage findByTransactionTime(@RequestParam(value = "beginTime", required = false) Timestamp beginTime,
                                                       @RequestParam(value = "endTime", required = false) Timestamp endTime,
                                                       @RequestParam("page") Integer page,
                                                       @RequestParam("size") Integer size) {
        logger.info("query transaction");
        Pageable pageable = new PageRequest(page - 1, size);
        Page<FxTransactionRecord> transactionRecords = transactionRecordService.findByTransactionTime(beginTime, endTime, pageable);
        TransactionRecordPage transactionRecordPage = new TransactionRecordPage();
        transactionRecordPage.setSize(transactionRecords.getTotalElements());
        transactionRecordPage.setPageIndex(transactionRecords.getNumber() + 1);
        transactionRecordPage.setPageSize(transactionRecords.getTotalPages());
        transactionRecordPage.setPageContent(transactionRecords.getContent());
        transactionRecordPage.setCurrencyPageSize(transactionRecords.getNumberOfElements());
        return transactionRecordPage;
    }

    @GetMapping("/bankcard")
    public TransactionRecordPage query(@RequestParam("bankcardID") String bankcardID,
                                       @RequestParam(value = "currencyCode", required = false) String currencyCode,
                                       @RequestParam(value = "type", required = false) Integer type,
                                       @RequestParam(value = "beginTime", required = false) Timestamp beginTime,
                                       @RequestParam(value = "endTime", required = false) Timestamp endTime,
                                       @RequestParam("page") Integer page,
                                       @RequestParam("size") Integer size) {
        logger.info("query transaction");
        Pageable pageable = new PageRequest(page - 1, size);
        Page<FxTransactionRecord> transactionRecords = transactionRecordService.query(bankcardID, currencyCode, type, beginTime, endTime, pageable);
        TransactionRecordPage transactionRecordPage = new TransactionRecordPage();
        transactionRecordPage.setSize(transactionRecords.getTotalElements());
        transactionRecordPage.setPageIndex(transactionRecords.getNumber() + 1);
        transactionRecordPage.setPageSize(transactionRecords.getTotalPages());
        transactionRecordPage.setPageContent(transactionRecords.getContent());
        transactionRecordPage.setCurrencyPageSize(transactionRecords.getNumberOfElements());
        return transactionRecordPage;
    }

    @PostMapping
    public FxTransactionRecord create(@RequestBody FxTransactionRecord transactionRecord) {
        logger.info("create transaction");
        return transactionRecordService.create(transactionRecord);
    }
}
