package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import com.shiep.fxbankcard.repository.FxTransactionRecordRepository;
import com.shiep.fxbankcard.service.IFxTransactionRecordService;
import com.shiep.fxbankcard.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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
    public List<FxTransactionRecord> findByType(Integer type) {
        return transactionRecordRepository.findByType(type);
    }

    @Override
    public List<FxTransactionRecord> findByCurrency(String currencyCode) {
        return transactionRecordRepository.findByCurrencyCode(currencyCode);
    }

    @Override
    public List<FxTransactionRecord> findByTransactionTime(Timestamp beginTime, Timestamp endTime) {
        String beginTimeValue = beginTime == null ? "0" : "1";
        String endTimeValue = endTime == null ? "0" : "1";
        StringBuffer buffer = new StringBuffer();
        buffer.append(beginTimeValue);
        buffer.append(endTimeValue);
        switch (buffer.toString()) {
            case "10":
                return transactionRecordRepository.findByTransactionTimeBefore(beginTime);
            case "01":
                return transactionRecordRepository.findByTransactionTimeAfter(endTime);
            case "11":
                return transactionRecordRepository.findByTransactionTimeBetween(beginTime, endTime);
            default:
                return null;
        }
    }

    @Override
    public List<FxTransactionRecord> query(String bankcardID, String currencyCode, Integer type, Timestamp beginTime, Timestamp endTime) {
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
                return transactionRecordRepository.findByBankcardId(bankcardID);
            case "0001":
                return transactionRecordRepository.findByBankcardIdAndTransactionTimeAfter(bankcardID, endTime);
            case "0010":
                return transactionRecordRepository.findByBankcardIdAndTransactionTimeBefore(bankcardID, beginTime);
            case "0011":
                return transactionRecordRepository.findByBankcardIdAndTransactionTimeBetween(bankcardID, beginTime, endTime);
            case "0100":
                return transactionRecordRepository.findByBankcardIdAndType(bankcardID, type);
            case "0101":
                return transactionRecordRepository.findByBankcardIdAndTypeAndTransactionTimeAfter(bankcardID, type, endTime);
            case "0110":
                return transactionRecordRepository.findByBankcardIdAndTypeAndTransactionTimeBefore(bankcardID, type, beginTime);
            case "0111":
                return transactionRecordRepository.findByBankcardIdAndTypeAndTransactionTimeBetween(bankcardID, type, beginTime, endTime);
            case "1000":
                return transactionRecordRepository.findByBankcardIdAndCurrencyCode(bankcardID, currencyCode);
            case "1001":
                return transactionRecordRepository.findByBankcardIdAndCurrencyCodeAndTransactionTimeAfter(bankcardID, currencyCode, endTime);
            case "1010":
                return transactionRecordRepository.findByBankcardIdAndCurrencyCodeAndTransactionTimeBefore(bankcardID, currencyCode, beginTime);
            case "1011":
                return transactionRecordRepository.findByBankcardIdAndCurrencyCodeAndTransactionTimeBetween(bankcardID, currencyCode, beginTime, endTime);
            case "1100":
                return transactionRecordRepository.findByBankcardIdAndCurrencyCodeAndType(bankcardID, currencyCode, type);
            case "1101":
                return transactionRecordRepository.findByBankcardIdAndTypeAndCurrencyCodeAndTransactionTimeAfter(bankcardID, type, currencyCode, endTime);
            case "1110":
                return transactionRecordRepository.findByBankcardIdAndTypeAndCurrencyCodeAndTransactionTimeBefore(bankcardID, type, currencyCode, beginTime);
            case "1111":
                return transactionRecordRepository.findByBankcardIdAndTypeAndCurrencyCodeAndTransactionTimeBetween(bankcardID, type, currencyCode, beginTime, endTime);
            default:
                return null;
        }
    }
}
