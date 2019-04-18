package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxCurrency;
import com.shiep.fxbankcard.repository.FxCurrencyRepository;
import com.shiep.fxbankcard.service.IFxCurrencyService;
import com.shiep.fxbankcard.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 13:44
 * @description: IFxCurrencyService的实现类
 */
@Service
public class FxCurrencyServiceImpl implements IFxCurrencyService {
    @Autowired
    private FxCurrencyRepository currencyRepository;

    @Override
    public FxCurrency create(FxCurrency currency) {
        // 该货币不存在时，才能进行创建
        if (findByCurrencyCode(currency.getEnglishName()) == null) {
            currency.setId(UuidTools.getUUID());
            return currencyRepository.save(currency);
        }
        return null;
    }

    @Override
    public FxCurrency delete(String currencyCode) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currencyRepository.delete(currency);
            return currency;
        }
        return null;
    }

    @Override
    public Boolean updateCurrencyDIR(String currencyCode, BigDecimal currencyDIR) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currency.setDepositInterestRate(currencyDIR);
            currencyRepository.save(currency);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCurrencyTDR1(String currencyCode, BigDecimal currencyTDR1) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currency.setTermDepositRateOfOneMonth(currencyTDR1);
            currencyRepository.save(currency);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCurrencyTDR3(String currencyCode, BigDecimal currencyTDR3) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currency.setTermDepositRateOfThreeMonth(currencyTDR3);
            currencyRepository.save(currency);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCurrencyTDR6(String currencyCode, BigDecimal currencyTDR6) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currency.setTermDepositRateOfSixMonth(currencyTDR6);
            currencyRepository.save(currency);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCurrencyTDR12(String currencyCode, BigDecimal currencyTDR12) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currency.setTermDepositRateOfOneYear(currencyTDR12);
            currencyRepository.save(currency);
            return true;
        }
        return false;
    }

    @Override
    public FxCurrency findByCurrencyName(String currencyName) {
        return currencyRepository.findByChinaName(currencyName);
    }

    @Override
    public FxCurrency findByCurrencyCode(String currencyCode) {
        return currencyRepository.findByEnglishName(currencyCode);
    }

    @Override
    public List<FxCurrency> getAll() {
        return currencyRepository.findAll();
    }
}
