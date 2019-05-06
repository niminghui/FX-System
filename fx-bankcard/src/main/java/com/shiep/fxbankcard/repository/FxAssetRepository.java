package com.shiep.fxbankcard.repository;

import com.shiep.fxbankcard.entity.FxAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    /**
     * description: 修改余额
     *
     * @param balance      余额
     * @param version      版本号
     * @param currencyCode 币种
     * @param bankcardId   银行卡号码
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update FX_ASSET set ASSET_BALANCE = ?1 , VERSION = (?2 + 1) where VERSION = ?2 and CURRENCY_EN_NAME = ?3 and BANKCARD_ID = ?4", nativeQuery = true)
    int updateBalance(BigDecimal balance, Integer version, String currencyCode, String bankcardId);
}
