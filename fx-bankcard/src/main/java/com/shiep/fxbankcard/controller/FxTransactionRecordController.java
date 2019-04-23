package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import com.shiep.fxbankcard.service.IFxTransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/23 15:46
 * @description: 交易记录控制层
 */
@RestController
@RequestMapping(path = "/record", produces = "application/json;charset=utf-8")
public class FxTransactionRecordController {

    @Autowired
    private IFxTransactionRecordService transactionRecordService;

    @GetMapping("/type")
    public List<FxTransactionRecord> findByType(@RequestParam("type") Integer type) {
        return transactionRecordService.findByType(type);
    }

    @GetMapping("/currency")
    public List<FxTransactionRecord> findByCurrency(@RequestParam("currencyCode") String currencyCode) {
        return transactionRecordService.findByCurrency(currencyCode);
    }

    @GetMapping("/time")
    public List<FxTransactionRecord> findByTransactionTime(@RequestParam(value = "beginTime", required = false) Timestamp beginTime,
                                                           @RequestParam(value = "endTime", required = false) Timestamp endTime) {
        return transactionRecordService.findByTransactionTime(beginTime, endTime);
    }

    @GetMapping("/bankcard")
    public List<FxTransactionRecord> query(@RequestParam("bankcardID") String bankcardID,
                                           @RequestParam(value = "currencyCode", required = false) String currencyCode,
                                           @RequestParam(value = "type", required = false) Integer type,
                                           @RequestParam(value = "beginTime", required = false) Timestamp beginTime,
                                           @RequestParam(value = "endTime", required = false) Timestamp endTime) {
        return transactionRecordService.query(bankcardID, currencyCode, type, beginTime, endTime);
    }

    @PostMapping
    public FxTransactionRecord create(@RequestBody FxTransactionRecord transactionRecord) {
        return transactionRecordService.create(transactionRecord);
    }
}
