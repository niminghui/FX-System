package com.shiep.fxbankcard.service;

import com.shiep.fxbankcard.entity.FxCurrency;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 13:20
 * @description: 货币服务接口
 */
@Transactional(rollbackFor = Exception.class)
public interface IFxCurrencyService {

    /**
     * description: 初始化货币种类
     *
     * @param
     * @return java.util.List<com.shiep.fxbankcard.entity.FxCurrency>
     */
    List<FxCurrency> init();

    /**
     * description: 创建货币
     *
     * @param currency 货币对象
     * @return com.shiep.fxbankcard.entity.FxCurrency
     */
    FxCurrency create(FxCurrency currency);

    /**
     * description: 删除货币
     *
     * @param currencyCode 货币的标准符号
     * @return com.shiep.fxbankcard.entity.FxCurrency
     */
    FxCurrency delete(String currencyCode);

    /**
     * description: 更新货币的活期利率
     *
     * @param currencyCode 货币的标准符号
     * @param currencyDIR  货币的活期利率
     * @return java.lang.Boolean
     */
    Boolean updateCurrencyDIR(String currencyCode, BigDecimal currencyDIR);

    /**
     * description: 更新货币的一个月定期利率
     *
     * @param currencyCode 货币的标准符号
     * @param currencyTDR1 货币的一个月定期利率
     * @return java.lang.Boolean
     */
    Boolean updateCurrencyTDR1(String currencyCode, BigDecimal currencyTDR1);

    /**
     * description: 更新货币的三个月定期利率
     *
     * @param currencyCode 货币的标准符号
     * @param currencyTDR3 货币的三个月定期利率
     * @return java.lang.Boolean
     */
    Boolean updateCurrencyTDR3(String currencyCode, BigDecimal currencyTDR3);

    /**
     * description: 更新货币的六个月定期利率
     *
     * @param currencyCode 货币的标准符号
     * @param currencyTDR6 货币的六个月定期利率
     * @return java.lang.Boolean
     */
    Boolean updateCurrencyTDR6(String currencyCode, BigDecimal currencyTDR6);

    /**
     * description: 更新货币的一年定期利率
     *
     * @param currencyCode  货币的标准符号
     * @param currencyTDR12 货币的一年定期利率
     * @return java.lang.Boolean
     */
    Boolean updateCurrencyTDR12(String currencyCode, BigDecimal currencyTDR12);

    /**
     * description: 通过货币名查找货币
     *
     * @param currencyName 货币名
     * @return com.shiep.fxbankcard.entity.FxCurrency
     */
    FxCurrency findByCurrencyName(String currencyName);

    /**
     * description: 通过货币的标准符号查找货币
     *
     * @param currencyCode 货币的标准符号
     * @return com.shiep.fxbankcard.entity.FxCurrency
     */
    FxCurrency findByCurrencyCode(String currencyCode);

    /**
     * description: 得到所有货币
     *
     * @param
     * @return java.util.List<com.shiep.fxbankcard.entity.FxCurrency>
     */
    List<FxCurrency> getAll();
}
