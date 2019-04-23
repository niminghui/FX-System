package com.shiep.fxbankcard.service;

import com.shiep.fxbankcard.entity.FxCurrencyPairs;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 15:03
 * @description: 货币对服务接口
 */
@Transactional(rollbackFor = Exception.class)
public interface IFxCurrencyPairsService {

    /**
     * description: 货币对初始化
     *
     * @param
     * @return java.util.List<com.shiep.fxbankcard.entity.FxCurrencyPairs>
     */
    List<FxCurrencyPairs> init();
    /**
     * description: 创建货币对
     *
     * @param currencyPairs 货币对
     * @return com.shiep.fxbankcard.entity.FxCurrencyPairs
     */
    FxCurrencyPairs create(FxCurrencyPairs currencyPairs);

    /**
     * description: 删除货币对
     *
     * @param basicCurrency     基本货币
     * @param secondaryCurrency 二级货币
     * @return com.shiep.fxbankcard.entity.FxCurrencyPairs
     */
    FxCurrencyPairs delete(String basicCurrency, String secondaryCurrency);

    /**
     * description: 通过基本货币和二级货币查找货币对
     *
     * @param basicCurrency     基本货币
     * @param secondaryCurrency 二级货币
     * @return com.shiep.fxbankcard.entity.FxCurrencyPairs
     */
    FxCurrencyPairs get(String basicCurrency, String secondaryCurrency);

    /**
     * description: 返回所有货币对
     *
     * @param
     * @return java.util.List<com.shiep.fxbankcard.entity.FxCurrencyPairs>
     */
    List<FxCurrencyPairs> getAll();
}
