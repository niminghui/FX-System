package com.shiep.fxbankcard.repository;

import com.shiep.fxbankcard.entity.FxCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 11:26
 * @description: FxCurrency的数据访问层
 */
@Repository
public interface FxCurrencyRepository extends JpaRepository<FxCurrency,String> {
    /**
     * description: 通过货币的中文名查找
     *
     * @param currencyName
     * @return com.shiep.fxbankcard.entity.FxCurrency
     */
    FxCurrency findByChinaName(String currencyName);

    /**
     * description: 通过货币码查找
     *
     * @param currencyCode
     * @return com.shiep.fxbankcard.entity.FxCurrency
     */
    FxCurrency findByEnglishName(String currencyCode);
}
