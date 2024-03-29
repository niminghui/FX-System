package com.shiep.fxbankcard.repository;

import com.shiep.fxbankcard.entity.FxCurrencyPairs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 11:31
 * @description: FxCurrencyPairs的数据访问层
 */
@Repository
public interface FxCurrencyPairsRepository extends JpaRepository<FxCurrencyPairs, String> {

    /**
     * description: 通过基础货币查找货币对
     *
     * @param basicCurrency 基本货币
     * @return com.shiep.fxbankcard.entity.FxCurrencyPairs
     */
    FxCurrencyPairs findByBasicCurrency(String basicCurrency);

    /**
     * description: 通过基本货币和二级货币查找货币对
     *
     * @param basicCurrency     基本货币
     * @param secondaryCurrency 二级货币
     * @return com.shiep.fxbankcard.entity.FxCurrencyPairs
     */
    FxCurrencyPairs findByBasicCurrencyAndSecondaryCurrency(String basicCurrency, String secondaryCurrency);
}
