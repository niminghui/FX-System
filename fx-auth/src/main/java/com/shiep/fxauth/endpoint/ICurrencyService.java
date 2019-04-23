package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.model.FxCurrency;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 16:41
 * @description: 货币服务端点
 */
@RequestMapping("/currency")
public interface ICurrencyService {

    /**
     * description: 得到所有货币
     *
     * @param
     * @return java.util.List<com.shiep.fxbankcard.entity.FxCurrency>
     */
    @GetMapping
    List<FxCurrency> getAll();

    /**
     * description: 货币初始化
     *
     * @param
     * @return java.util.List<com.shiep.fxauth.model.FxCurrency>
     */
    @GetMapping("/init")
    List<FxCurrency> initCurrency();

    /**
     * description: 通过货币名查找货币
     *
     * @param currencyName 货币名
     * @return com.shiep.fxbankcard.entity.FxCurrency
     */
    @GetMapping("/name/{currencyName}")
    FxCurrency getFxCurrencyByName(@PathVariable("currencyName") String currencyName);

    /**
     * description: 通过货币的标准符号查找货币
     *
     * @param currencyCode 货币的标准符号
     * @return com.shiep.fxbankcard.entity.FxCurrency
     */
    @GetMapping("/code/{currencyCode}")
    FxCurrency getFxCurrencyByCode(@PathVariable("currencyCode") String currencyCode);

    /**
     * description: 创建货币
     *
     * @param currency 货币对象
     * @return com.shiep.fxbankcard.entity.FxCurrency
     */
    @PostMapping
    FxCurrency create(FxCurrency currency);

    /**
     * description: 更新货币的利率
     *
     * @param currencyCode 货币的标准符号
     * @param type         类型
     * @param value        新的货币汇率
     * @return java.lang.Boolean
     */
    @PutMapping("/{currencyCode}/{type}/{value}")
    Boolean updateRate(@PathVariable("currencyCode") String currencyCode, @PathVariable("type") Integer type,
                       @PathVariable("value") BigDecimal value);

    /**
     * description: 删除货币
     *
     * @param currencyCode 货币的标准符号
     * @return com.shiep.fxbankcard.entity.FxCurrency
     */
    @DeleteMapping("/{currencyCode}")
    FxCurrency delete(@PathVariable("currencyCode") String currencyCode);
}
