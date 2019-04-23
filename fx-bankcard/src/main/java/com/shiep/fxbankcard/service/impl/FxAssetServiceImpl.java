package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxAsset;
import com.shiep.fxbankcard.repository.FxAssetRepository;
import com.shiep.fxbankcard.service.IFxAssetService;
import com.shiep.fxbankcard.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/23 9:54
 * @description: 资产服务接口的实现类
 */
@Service
public class FxAssetServiceImpl implements IFxAssetService {

    @Autowired
    private FxAssetRepository assetRepository;

    @Override
    public FxAsset create(FxAsset asset) {
        if (get(asset.getBankcardId(), asset.getCurrencyCode()) == null) {
            asset.setId(UuidTools.getUUID());
            return assetRepository.save(asset);
        }
        return null;
    }

    @Override
    public FxAsset updateBalance(String bankcardID, String currencyCode, BigDecimal newBalance) {
        FxAsset result = get(bankcardID, currencyCode);
        if (result == null) {
            FxAsset asset = new FxAsset();
            asset.setId(UuidTools.getUUID());
            asset.setBankcardId(bankcardID);
            asset.setCurrencyCode(currencyCode);
            asset.setBalance(newBalance);
            return assetRepository.save(asset);
        }
        result.setBalance(newBalance);
        return assetRepository.save(result);
    }

    @Override
    public FxAsset get(String bankcardID, String currencyCode) {
        return assetRepository.findByBankcardIdAndCurrencyCode(bankcardID, currencyCode);
    }

    @Override
    public List<FxAsset> getAll(String bankcardID) {
        return assetRepository.findByBankcardId(bankcardID);
    }
}
