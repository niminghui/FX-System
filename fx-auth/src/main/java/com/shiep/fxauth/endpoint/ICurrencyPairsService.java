package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.model.FxCurrencyPairs;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/22 13:45
 * @description: 货币对服务端点
 */
@RequestMapping("/currencyPairs")
public interface ICurrencyPairsService {
    /**
     * description: 得到所有货币对
     *
     * @param
     * @return java.util.List<com.shiep.fxauth.model.FxCurrencyPairs>
     */
    @GetMapping
    List<FxCurrencyPairs> getAll();

    /**
     * description: 货币对初始化
     *
     * @param
     * @return java.util.List<com.shiep.fxauth.model.FxCurrencyPairs>
     */
    @GetMapping("/init")
    List<FxCurrencyPairs> initCurrencyPairs();

    /**
     * description: 创建货币对
     *
     * @param currencyPairs 货币对
     * @return com.shiep.fxauth.model.FxCurrencyPairs
     */
    @PostMapping
    FxCurrencyPairs create(FxCurrencyPairs currencyPairs);

    /**
     * description: 删除货币对
     *
     * @param currencyPairs 货币对
     * @return com.shiep.fxauth.model.FxCurrencyPairs
     */
    @DeleteMapping
    FxCurrencyPairs delete(FxCurrencyPairs currencyPairs);
}
