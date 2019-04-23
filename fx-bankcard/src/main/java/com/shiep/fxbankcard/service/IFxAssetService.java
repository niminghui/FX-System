package com.shiep.fxbankcard.service;

import com.shiep.fxbankcard.entity.FxAsset;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/23 9:39
 * @description: 资产服务接口
 */
@Transactional(rollbackFor = Exception.class)
public interface IFxAssetService {

    /**
     * description: 先根据银行卡和货币码查找，如果存在，则创建失败，否则创建该资产。
     *
     * @param asset 资产
     * @return com.shiep.fxbankcard.entity.FxAsset
     */
    FxAsset create(FxAsset asset);

    /**
     * description: 先通过银行卡号码和货币码查找该资产，如果不存在则创建，存在则直接修改余额
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @param newBalance   新余额
     * @return com.shiep.fxbankcard.entity.FxAsset
     */
    FxAsset updateBalance(String bankcardID, String currencyCode, BigDecimal newBalance);

    /**
     * description: 通过银行卡号码和货币码查找该资产
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @return com.shiep.fxbankcard.entity.FxAsset
     */
    FxAsset get(String bankcardID, String currencyCode);

    /**
     * description: 通过银行卡号码查找该卡下的所有资产
     *
     * @param bankcardID 银行卡号码
     * @return java.util.List<com.shiep.fxbankcard.entity.FxAsset>
     */
    List<FxAsset> getAll(String bankcardID);
}
