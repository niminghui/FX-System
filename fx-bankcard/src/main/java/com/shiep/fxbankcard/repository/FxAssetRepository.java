package com.shiep.fxbankcard.repository;

import com.shiep.fxbankcard.entity.FxAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 10:46
 * @description: FxAsset的数据访问层
 */
@Repository
public interface FxAssetRepository extends JpaRepository<FxAsset, String> {

    /**
     * description: 通过银行卡号查找该银行卡具有的资产
     *
     * @param bankcardID 银行卡号
     * @return java.util.List<com.shiep.fxbankcard.entity.FxAsset>
     */
    List<FxAsset> findByBankcardId(String bankcardID);

    /**
     * description: 通过银行卡号和货币码查找资产
     *
     * @param bankcardID 银行卡号
     * @param currencyCode 货币码
     * @return com.shiep.fxbankcard.entity.FxAsset
     */
    FxAsset findByBankcardIdAndCurrencyCode(String bankcardID, String currencyCode);
}
