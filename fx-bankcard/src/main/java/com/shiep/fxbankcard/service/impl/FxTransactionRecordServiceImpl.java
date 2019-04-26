package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import com.shiep.fxbankcard.repository.FxTransactionRecordRepository;
import com.shiep.fxbankcard.service.IFxTransactionRecordService;
import com.shiep.fxbankcard.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author: 倪明辉
 * @date: 2019/4/23 15:08
 * @description: 交易记录服务接口的实现类
 */
@Service
public class FxTransactionRecordServiceImpl implements IFxTransactionRecordService {
    @Autowired
    private FxTransactionRecordRepository transactionRecordRepository;

    @Override
    public FxTransactionRecord create(FxTransactionRecord transactionRecord) {
        transactionRecord.setId(UuidTools.getUUID());
        return transactionRecordRepository.save(transactionRecord);
    }

    @Override
    public Page<FxTransactionRecord> findByType(Integer type, Pageable pageable) {
        return transactionRecordRepository.findByType(type, pageable);
    }

    @Override
    public Page<FxTransactionRecord> findByCurrency(String currencyCode, Pageable pageable) {
        return transactionRecordRepository.findByCurrencyCode(currencyCode, pageable);
    }

    @Override
    public Page<FxTransactionRecord> findByTransactionTime(Timestamp beginTime, Timestamp endTime, Pageable pageable) {
        String beginTimeValue = beginTime == null ? "0" : "1";
        String endTimeValue = endTime == null ? "0" : "1";
        StringBuffer buffer = new StringBuffer();
        buffer.append(beginTimeValue);
        buffer.append(endTimeValue);
        switch (buffer.toString()) {
            case "10":
                return transactionRecordRepository.findByTransactionTimeBefore(beginTime, pageable);
            case "01":
                return transactionRecordRepository.findByTransactionTimeAfter(endTime, pageable);
            case "11":
                return transactionRecordRepository.findByTransactionTimeBetween(beginTime, endTime, pageable);
            default:
                return null;
        }
    }

    @Override
    public Page<FxTransactionRecord> query(String bankcardID, String currencyCode, Integer type, Timestamp beginTime,
                                           Timestamp endTime, Pageable pageable) {
        if (bankcardID == null) {
            return null;
        }
        String currencyValue = currencyCode == null ? "0" : "1";
        String typeValue = type == null ? "0" : "1";
        String beginTimeValue = beginTime == null ? "0" : "1";
        String endTimeValue = endTime == null ? "0" : "1";
        StringBuffer buffer = new StringBuffer();
        buffer.append(currencyValue);
        buffer.append(typeValue);
        buffer.append(beginTimeValue);
        buffer.append(endTimeValue);
        switch (buffer.toString()) {
            case "0000":
                return transactionRecordRepository.findByBankcardId(bankcardID, pageable);
            case "0001":
                return transactionRecordRepository.findByBankcardIdAndTransactionTimeAfter(bankcardID, endTime, pageable);
            case "0010":
                return transactionRecordRepository.findByBankcardIdAndTransactionTimeBefore(bankcardID, beginTime, pageable);
            case "0011":
                return transactionRecordRepository.findByBankcardIdAndTransactionTimeBetween(bankcardID, beginTime, endTime, pageable);
            case "0100":
                return transactionRecordRepository.findByBankcardIdAndType(bankcardID, type, pageable);
            case "0101":
                return transactionRecordRepository.findByBankcardIdAndTypeAndTransactionTimeAfter(bankcardID, type, endTime, pageable);
            case "0110":
                return transactionRecordRepository.findByBankcardIdAndTypeAndTransactionTimeBefore(bankcardID, type, beginTime, pageable);
            case "0111":
                return transactionRecordRepository.findByBankcardIdAndTypeAndTransactionTimeBetween(bankcardID, type, beginTime, endTime, pageable);
            case "1000":
                return transactionRecordRepository.findByBankcardIdAndCurrencyCode(bankcardID, currencyCode, pageable);
            case "1001":
                return transactionRecordRepository.findByBankcardIdAndCurrencyCodeAndTransactionTimeAfter(bankcardID, currencyCode, endTime, pageable);
            case "1010":
                return transactionRecordRepository.findByBankcardIdAndCurrencyCodeAndTransactionTimeBefore(bankcardID, currencyCode, beginTime, pageable);
            case "1011":
                return transactionRecordRepository.findByBankcardIdAndCurrencyCodeAndTransactionTimeBetween(bankcardID, currencyCode, beginTime, endTime, pageable);
            case "1100":
                return transactionRecordRepository.findByBankcardIdAndCurrencyCodeAndType(bankcardID, currencyCode, type, pageable);
            case "1101":
                return transactionRecordRepository.findByBankcardIdAndTypeAndCurrencyCodeAndTransactionTimeAfter(bankcardID, type, currencyCode, endTime, pageable);
            case "1110":
                return transactionRecordRepository.findByBankcardIdAndTypeAndCurrencyCodeAndTransactionTimeBefore(bankcardID, type, currencyCode, beginTime, pageable);
            case "1111":
                return transactionRecordRepository.findByBankcardIdAndTypeAndCurrencyCodeAndTransactionTimeBetween(bankcardID, type, currencyCode, beginTime, endTime, pageable);
            default:
                return null;
        }
    }
}
