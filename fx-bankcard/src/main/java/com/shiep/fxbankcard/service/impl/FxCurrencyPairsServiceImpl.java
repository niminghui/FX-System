package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxCurrencyPairs;
import com.shiep.fxbankcard.repository.FxCurrencyPairsRepository;
import com.shiep.fxbankcard.service.IFxCurrencyPairsService;
import com.shiep.fxbankcard.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 15:48
 * @description: IFxCurrencyPairsService的实现类
 */
@Service
public class FxCurrencyPairsServiceImpl implements IFxCurrencyPairsService {
    @Autowired
    private FxCurrencyPairsRepository currencyPairsRepository;

    @Override
    public FxCurrencyPairs create(FxCurrencyPairs currencyPairs) {
        if (get(currencyPairs.getBasicCurrency(), currencyPairs.getSecondaryCurrency()) != null) {
            currencyPairs.setId(UuidTools.getUUID());
            return currencyPairsRepository.save(currencyPairs);
        }
        return null;
    }

    @Override
    public FxCurrencyPairs delete(String basicCurrency, String secondaryCurrency) {
        FxCurrencyPairs currencyPairs = get(basicCurrency, secondaryCurrency);
        if (currencyPairs != null) {
            currencyPairsRepository.delete(currencyPairs);
            return currencyPairs;
        }
        return null;
    }

    @Override
    public FxCurrencyPairs get(String basicCurrency, String secondaryCurrency) {
        return currencyPairsRepository.findByBasicCurrencyAndSecondaryCurrency(basicCurrency, secondaryCurrency);
    }

    @Override
    public List<FxCurrencyPairs> getAll() {
        return currencyPairsRepository.findAll();
    }
}
