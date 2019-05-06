package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxAsset;
import com.shiep.fxbankcard.entity.FxCurrency;
import com.shiep.fxbankcard.entity.FxTransactionRecord;
import com.shiep.fxbankcard.model.TransactionTypeEnum;
import com.shiep.fxbankcard.repository.FxAssetRepository;
import com.shiep.fxbankcard.repository.FxBankCardRepository;
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
    @Autowired
    private FxBankCardRepository bankCardRepository;

    private static final int COUNT = 3;

    @Override
    public FxTransactionRecord calculateCurrentInterest(String bankcardID, String currencyCode) {
        // 如果该银行卡不存在
        if (!bankCardRepository.findById(bankcardID).isPresent()) {
            return null;
        }
        // 最近一次的利息存入时间
        Timestamp beginTime = recordRepository.getTheMostRecentDepositTime(bankcardID, currencyCode);
        // 当前时间
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        // 当前账户余额
        BigDecimal balance = assetRepository.findByBankcardIdAndCurrencyCode(bankcardID, currencyCode).getBalance();
        // 交易记录
        List<FxTransactionRecord> recordList;
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
            if (TransactionTypeEnum.TRANSFER_EXPENDITURE.getCode().equals(record.getType()) || TransactionTypeEnum.EXCHANGE_EXPENDITURE.getCode().equals(record.getType())) {
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
                .divide(new BigDecimal(36000), 4, BigDecimal.ROUND_HALF_UP);
        // 如果利息为0，则返回null
        if (depositInterest.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        // 乐观锁CAS的重入机制
        for (int i = 0; i < COUNT; i++) {
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
            transactionRecord.setType(TransactionTypeEnum.DEPOSIT_INTEREST_INTO.getCode());
            return recordRepository.save(transactionRecord);
        }
        return null;
    }

    @Override
    public FxTransactionRecord deposit(String bankcardID, String currencyCode, BigDecimal money) {
        // 如果该银行卡不存在
        if (!bankCardRepository.findById(bankcardID).isPresent()) {
            return null;
        }
        for (int i = 0; i < COUNT; i++) {
            FxAsset asset = assetRepository.findByBankcardIdAndCurrencyCode(bankcardID, currencyCode);
            if (asset == null) {
                asset = create(bankcardID, currencyCode);
            }
            int version = asset.getVersion();
            BigDecimal totalBalance = asset.getBalance().add(money);
            int result = assetRepository.updateBalance(totalBalance, version, currencyCode, bankcardID);
            if (result == 0) {
                continue;
            }
            FxTransactionRecord transactionRecord = new FxTransactionRecord();
            transactionRecord.setId(UuidTools.getUUID());
            transactionRecord.setBankcardId(bankcardID);
            transactionRecord.setCurrencyCode(currencyCode);
            transactionRecord.setMoney(money);
            transactionRecord.setTransactionPeople(bankcardID);
            transactionRecord.setTransactionTime(new Timestamp(System.currentTimeMillis()));
            transactionRecord.setType(TransactionTypeEnum.DEPOSIT_INFO.getCode());
            return recordRepository.save(transactionRecord);
        }
        return null;
    }

    @Override
    public FxTransactionRecord transfer(String bankcardID, String currencyCode, BigDecimal money, String otherBankcardID) {
        // 如果该银行卡不存在
        if (!bankCardRepository.findById(bankcardID).isPresent() || !bankCardRepository.findById(otherBankcardID).isPresent()) {
            return null;
        }
        // 如果当前账户余额不足
        if (assetRepository.findByBankcardIdAndCurrencyCode(bankcardID, currencyCode).getBalance().compareTo(money) < 0) {
            return null;
        }
        // 乐观锁CAS的重入机制
        for (int i = 0; i < COUNT; i++) {
            FxAsset asset = assetRepository.findByBankcardIdAndCurrencyCode(bankcardID, currencyCode);
            FxAsset otherAsset = assetRepository.findByBankcardIdAndCurrencyCode(otherBankcardID, currencyCode);
            if (otherAsset == null) {
                otherAsset = create(otherBankcardID, currencyCode);
            }
            int result = assetRepository.updateBalance(asset.getBalance().subtract(money), asset.getVersion(), currencyCode, bankcardID);
            int otherResult = assetRepository.updateBalance(otherAsset.getBalance().add(money), otherAsset.getVersion(), currencyCode, otherBankcardID);
            if (result == 0 || otherResult == 0) {
                continue;
            }
            Timestamp now = new Timestamp(System.currentTimeMillis());
            FxTransactionRecord otherRecord = new FxTransactionRecord();
            otherRecord.setId(UuidTools.getUUID());
            otherRecord.setBankcardId(otherBankcardID);
            otherRecord.setCurrencyCode(currencyCode);
            otherRecord.setMoney(money);
            otherRecord.setTransactionPeople(bankcardID);
            otherRecord.setTransactionTime(now);
            otherRecord.setType(TransactionTypeEnum.TRANSFER_INTO.getCode());
            recordRepository.save(otherRecord);

            FxTransactionRecord record = new FxTransactionRecord();
            record.setId(UuidTools.getUUID());
            record.setBankcardId(bankcardID);
            record.setCurrencyCode(currencyCode);
            record.setMoney(money);
            record.setTransactionPeople(otherBankcardID);
            record.setTransactionTime(now);
            record.setType(TransactionTypeEnum.TRANSFER_EXPENDITURE.getCode());
            return recordRepository.save(record);
        }
        return null;
    }

    @Override
    public Boolean foreignExchangeTrading(String bankcardID, String basicCurrency, String secondaryCurrency,
                                          BigDecimal money, BigDecimal rate, Boolean buy) {
        // 如果该银行卡不存在
        if (!bankCardRepository.findById(bankcardID).isPresent()) {
            return null;
        }
        FxAsset basicAsset = assetRepository.findByBankcardIdAndCurrencyCode(bankcardID, basicCurrency);
        FxAsset secondaryAsset = assetRepository.findByBankcardIdAndCurrencyCode(bankcardID, secondaryCurrency);
        if (basicAsset == null) {
            basicAsset = create(bankcardID, secondaryCurrency);
        }
        if (secondaryAsset == null) {
            secondaryAsset = create(bankcardID, secondaryCurrency);
        }
        // 银行用二级货币买入基本货币==》用户用基本货币买二级货币
        if (buy) {
            // 如果基本货币余额不足
            if (basicAsset.getBalance().compareTo(money) < 0) {
                return false;
            }
            // 买入的二级货币金额
            BigDecimal calMoney = money.multiply(rate);
            int basicResult = assetRepository.updateBalance(basicAsset.getBalance().subtract(money), basicAsset.getVersion(), basicCurrency, bankcardID);
            int secondaryResult = assetRepository.updateBalance(secondaryAsset.getBalance().add(calMoney), secondaryAsset.getVersion(), secondaryCurrency, bankcardID);
            if (basicResult == 0 || secondaryResult == 0) {
                return false;
            }
            FxTransactionRecord basicRecord = new FxTransactionRecord();
            basicRecord.setId(UuidTools.getUUID());
            basicRecord.setBankcardId(bankcardID);
            basicRecord.setCurrencyCode(basicCurrency);
            basicRecord.setMoney(money);
            basicRecord.setTransactionPeople(bankcardID);
            basicRecord.setTransactionTime(new Timestamp(System.currentTimeMillis()));
            // 外汇兑换支出
            basicRecord.setType(TransactionTypeEnum.EXCHANGE_EXPENDITURE.getCode());
            recordRepository.save(basicRecord);

            FxTransactionRecord secondaryRecord = new FxTransactionRecord();
            secondaryRecord.setId(UuidTools.getUUID());
            secondaryRecord.setBankcardId(bankcardID);
            secondaryRecord.setCurrencyCode(secondaryCurrency);
            secondaryRecord.setMoney(calMoney);
            secondaryRecord.setTransactionPeople(bankcardID);
            secondaryRecord.setTransactionTime(new Timestamp(System.currentTimeMillis()));
            // 外汇兑换存入
            secondaryRecord.setType(TransactionTypeEnum.EXCHANGE_INTO.getCode());
            recordRepository.save(secondaryRecord);

            return true;
        }
        // 银行用基本货币买入二级货币==》用户用二级货币买基本货币
        else {
            if (secondaryAsset.getBalance().compareTo(money) < 0) {
                return false;
            }
            BigDecimal calMoney = money.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
            int basicResult = assetRepository.updateBalance(basicAsset.getBalance().add(calMoney), basicAsset.getVersion(), basicCurrency, bankcardID);
            int secondaryResult = assetRepository.updateBalance(secondaryAsset.getBalance().subtract(money), secondaryAsset.getVersion(), secondaryCurrency, bankcardID);
            if (basicResult == 0 || secondaryResult == 0) {
                return false;
            }
            FxTransactionRecord basicRecord = new FxTransactionRecord();
            basicRecord.setId(UuidTools.getUUID());
            basicRecord.setBankcardId(bankcardID);
            basicRecord.setCurrencyCode(basicCurrency);
            basicRecord.setMoney(calMoney);
            basicRecord.setTransactionPeople(bankcardID);
            basicRecord.setTransactionTime(new Timestamp(System.currentTimeMillis()));
            // 外汇兑换存入
            basicRecord.setType(TransactionTypeEnum.EXCHANGE_INTO.getCode());
            recordRepository.save(basicRecord);

            FxTransactionRecord secondaryRecord = new FxTransactionRecord();
            secondaryRecord.setId(UuidTools.getUUID());
            secondaryRecord.setBankcardId(bankcardID);
            secondaryRecord.setCurrencyCode(secondaryCurrency);
            secondaryRecord.setMoney(money);
            secondaryRecord.setTransactionPeople(bankcardID);
            secondaryRecord.setTransactionTime(new Timestamp(System.currentTimeMillis()));
            // 外汇兑换支出
            secondaryRecord.setType(TransactionTypeEnum.EXCHANGE_EXPENDITURE.getCode());
            recordRepository.save(secondaryRecord);

            return true;
        }
    }

    @Override
    public Boolean foreignExchangeSettlement(String bankcardID, String currencyCode, BigDecimal money, BigDecimal rate) {
        return foreignExchangeTrading(bankcardID, currencyCode, "CNY", money,
                rate.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP), true);
    }

    @Override
    public Boolean foreignExchangePurchase(String bankcardID, String currencyCode, BigDecimal money, BigDecimal rate) {
        return foreignExchangeTrading(bankcardID, currencyCode, "CNY", money,
                rate.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP), false);
    }

    private FxAsset create(String bankcardID, String currencyCode) {
        FxAsset asset = new FxAsset();
        asset.setId(UuidTools.getUUID());
        asset.setBankcardId(bankcardID);
        asset.setCurrencyCode(currencyCode);
        asset.setBalance(new BigDecimal(0));
        asset.setVersion(0);
        return assetRepository.save(asset);
    }
}
