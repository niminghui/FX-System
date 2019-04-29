package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxAsset;
import com.shiep.fxbankcard.entity.FxCurrency;
import com.shiep.fxbankcard.entity.FxTransactionRecord;
import com.shiep.fxbankcard.repository.FxAssetRepository;
import com.shiep.fxbankcard.repository.FxCurrencyRepository;
import com.shiep.fxbankcard.repository.FxTransactionRecordRepository;
import com.shiep.fxbankcard.service.ITransactionService;
import com.shiep.fxbankcard.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/29 11:48
 * @description: 交易服务层的实现层（事务的隔离级别为读写提交）
 */
@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    private FxAssetRepository assetRepository;
    @Autowired
    private FxTransactionRecordRepository recordRepository;
    @Autowired
    private FxCurrencyRepository currencyRepository;

    @Override
    public FxTransactionRecord calculateCurrentInterest(String bankcardID, String currencyCode) {
        // 最近一次的利息存入时间
        Timestamp beginTime = recordRepository.getTheMostRecentDepositTime(bankcardID, currencyCode);
        // 当前时间
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        // 当前账户余额
        BigDecimal balance = assetRepository.findByBankcardIdAndCurrencyCode(bankcardID, currencyCode).getBalance();
        // 交易记录
        List<FxTransactionRecord> recordList = null;
        // 利息的元*日
        BigDecimal result = new BigDecimal(0);
        if (beginTime == null) {
            recordList = recordRepository.findByBankcardIdAndCurrencyCodeOrderByTransactionTimeDesc(bankcardID, currencyCode);
        } else {
            recordList = recordRepository.findByBankcardIdAndCurrencyCodeAndTransactionTimeAfterOrderByTransactionTimeDesc(bankcardID, currencyCode, beginTime);
        }
        for (FxTransactionRecord record : recordList) {
            // 该笔资金存在天数
            int days = (int) ((endTime.getTime() - record.getTransactionTime().getTime()) / (1000 * 3600 * 24));
            endTime = record.getTransactionTime();
            // 资金支出
            if (record.getType() == 0 || record.getType() == 1) {
                balance = balance.add(record.getMoney());
            }
            // 资金存入
            else {
                balance = balance.subtract(record.getMoney());
            }
            result = result.add(balance.multiply(new BigDecimal(days)));
        }
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        // 活期利息=各段积数的总和×日利率(保留四位小数点,四舍五入)
        BigDecimal depositInterest = result.multiply(currency.getDepositInterestRate())
                .divide(new BigDecimal(36000)).setScale(4, BigDecimal.ROUND_HALF_UP);
        // 如果利息为0，则返回null
        if (depositInterest.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        // 乐观锁的重入机制
        for (int i = 0; i < 3; i++) {
            FxAsset asset = assetRepository.findByBankcardIdAndCurrencyCode(bankcardID, currencyCode);
            int version = asset.getVersion();
            int res = assetRepository.updateBalance(asset.getBalance().add(depositInterest), version, currencyCode, bankcardID);
            if (res == 0) {
                continue;
            }
            FxTransactionRecord transactionRecord = new FxTransactionRecord();
            transactionRecord.setId(UuidTools.getUUID());
            transactionRecord.setBankcardId(bankcardID);
            transactionRecord.setCurrencyCode(currencyCode);
            transactionRecord.setMoney(depositInterest);
            transactionRecord.setTransactionPeople("FX-System");
            transactionRecord.setTransactionTime(new Timestamp(System.currentTimeMillis()));
            transactionRecord.setType(4);
            return recordRepository.save(transactionRecord);
        }
        return null;
    }

    @Override
    public FxTransactionRecord deposit(String bankcardID, String currencyCode, BigDecimal money) {
        return null;
    }

    @Override
    public FxTransactionRecord transfer(String bankcardID, String currencyCode, BigDecimal money, String otherBankcardID) {
        return null;
    }

    @Override
    public FxTransactionRecord foreignExchangeTrading(String bankcardID, String basicCurrency, String secondaryCurrency, BigDecimal money, BigDecimal rate, Boolean buy) {
        return null;
    }

    @Override
    public FxTransactionRecord foreignExchangeSettlement(String bankcardID, String currencyCode, BigDecimal money, BigDecimal rate) {
        return null;
    }

    @Override
    public FxTransactionRecord foreignExchangePurchase(String bankcardID, String currencyCode, BigDecimal money, BigDecimal rate) {
        return null;
    }

    private FxAsset insert(String bankcardID, String currency) {
        FxAsset asset = new FxAsset();
        asset.setId(UuidTools.getUUID());
        asset.setBankcardId(bankcardID);
        asset.setCurrencyCode(currency);
        asset.setBalance(new BigDecimal(0));
        asset.setVersion(0);
        return assetRepository.save(asset);
    }
}
