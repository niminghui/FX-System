package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxCurrency;
import com.shiep.fxbankcard.repository.FxCurrencyRepository;
import com.shiep.fxbankcard.service.IFxCurrencyService;
import com.shiep.fxbankcard.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 13:44
 * @description: IFxCurrencyService的实现类
 */
@Service
public class FxCurrencyServiceImpl implements IFxCurrencyService {
    @Autowired
    private FxCurrencyRepository currencyRepository;

    @Override
    public List<FxCurrency> init() {
        return currencyRepository.saveAll(getInitData());
    }

    @Override
    public FxCurrency create(FxCurrency currency) {
        // 该货币不存在时，才能进行创建
        if (findByCurrencyCode(currency.getEnglishName()) == null) {
            currency.setId(UuidTools.getUUID());
            return currencyRepository.save(currency);
        }
        return null;
    }

    @Override
    public FxCurrency delete(String currencyCode) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currencyRepository.delete(currency);
            return currency;
        }
        return null;
    }

    @Override
    public Boolean updateCurrencyDIR(String currencyCode, BigDecimal currencyDIR) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currency.setDepositInterestRate(currencyDIR);
            currencyRepository.save(currency);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCurrencyTDR1(String currencyCode, BigDecimal currencyTDR1) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currency.setTermDepositRateOfOneMonth(currencyTDR1);
            currencyRepository.save(currency);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCurrencyTDR3(String currencyCode, BigDecimal currencyTDR3) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currency.setTermDepositRateOfThreeMonth(currencyTDR3);
            currencyRepository.save(currency);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCurrencyTDR6(String currencyCode, BigDecimal currencyTDR6) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currency.setTermDepositRateOfSixMonth(currencyTDR6);
            currencyRepository.save(currency);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCurrencyTDR12(String currencyCode, BigDecimal currencyTDR12) {
        FxCurrency currency = currencyRepository.findByEnglishName(currencyCode);
        if (currency != null) {
            currency.setTermDepositRateOfOneYear(currencyTDR12);
            currencyRepository.save(currency);
            return true;
        }
        return false;
    }

    @Override
    public FxCurrency findByCurrencyName(String currencyName) {
        return currencyRepository.findByChinaName(currencyName);
    }

    @Override
    public FxCurrency findByCurrencyCode(String currencyCode) {
        return currencyRepository.findByEnglishName(currencyCode);
    }

    @Override
    public List<FxCurrency> getAll() {
        return currencyRepository.findAll();
    }

    /**
     * description: 返回初始货币集
     *
     * @param
     * @return java.util.List<com.shiep.fxbankcard.entity.FxCurrency>
     */
    private List<FxCurrency> getInitData() {
        List<FxCurrency> data = new ArrayList<>();
        // 人民币
        FxCurrency cny = new FxCurrency();
        cny.setId(UuidTools.getUUID());
        cny.setChinaName("人民币");
        cny.setEnglishName("CNY");
        cny.setCountry("中国");
        cny.setDepositInterestRate(new BigDecimal("0.3"));
        cny.setTermDepositRateOfOneMonth(new BigDecimal("1.15"));
        cny.setTermDepositRateOfThreeMonth(new BigDecimal("1.35"));
        cny.setTermDepositRateOfSixMonth(new BigDecimal("1.55"));
        cny.setTermDepositRateOfOneYear(new BigDecimal("1.75"));
        data.add(cny);
        // 澳大利亚元
        FxCurrency aud = new FxCurrency();
        aud.setId(UuidTools.getUUID());
        aud.setChinaName("澳大利亚元");
        aud.setEnglishName("AUD");
        aud.setCountry("澳大利亚");
        aud.setDepositInterestRate(new BigDecimal("0.2375"));
        aud.setTermDepositRateOfOneMonth(new BigDecimal("1.2400"));
        aud.setTermDepositRateOfThreeMonth(new BigDecimal("1.3125"));
        aud.setTermDepositRateOfSixMonth(new BigDecimal("1.3250"));
        aud.setTermDepositRateOfOneYear(new BigDecimal("1.5000"));
        data.add(aud);
        // 美元
        FxCurrency usd = new FxCurrency();
        usd.setId(UuidTools.getUUID());
        usd.setChinaName("美元");
        usd.setEnglishName("USD");
        usd.setCountry("美国");
        usd.setDepositInterestRate(new BigDecimal("0.0500"));
        usd.setTermDepositRateOfOneMonth(new BigDecimal("0.2000"));
        usd.setTermDepositRateOfThreeMonth(new BigDecimal("0.3000"));
        usd.setTermDepositRateOfSixMonth(new BigDecimal("0.5000"));
        usd.setTermDepositRateOfOneYear(new BigDecimal("0.7500"));
        data.add(usd);
        // 欧元
        FxCurrency eur = new FxCurrency();
        eur.setId(UuidTools.getUUID());
        eur.setChinaName("欧元");
        eur.setEnglishName("EUR");
        eur.setCountry("欧洲");
        eur.setDepositInterestRate(new BigDecimal("0.0001"));
        eur.setTermDepositRateOfOneMonth(new BigDecimal("0.0100"));
        eur.setTermDepositRateOfThreeMonth(new BigDecimal("0.0100"));
        eur.setTermDepositRateOfSixMonth(new BigDecimal("0.0100"));
        eur.setTermDepositRateOfOneYear(new BigDecimal("0.0100"));
        data.add(eur);
        // 英镑
        FxCurrency gbp = new FxCurrency();
        gbp.setId(UuidTools.getUUID());
        gbp.setChinaName("英镑");
        gbp.setEnglishName("GBP");
        gbp.setCountry("英国");
        gbp.setDepositInterestRate(new BigDecimal("0.0500"));
        gbp.setTermDepositRateOfOneMonth(new BigDecimal("0.1000"));
        gbp.setTermDepositRateOfThreeMonth(new BigDecimal("0.1000"));
        gbp.setTermDepositRateOfSixMonth(new BigDecimal("0.1000"));
        gbp.setTermDepositRateOfOneYear(new BigDecimal("0.1000"));
        data.add(gbp);
        // 新西兰元
        FxCurrency nzd = new FxCurrency();
        nzd.setId(UuidTools.getUUID());
        nzd.setChinaName("新西兰元");
        nzd.setEnglishName("NZD");
        nzd.setCountry("新西兰");
        nzd.setDepositInterestRate(new BigDecimal("0.0001"));
        nzd.setTermDepositRateOfOneMonth(new BigDecimal("0.0100"));
        nzd.setTermDepositRateOfThreeMonth(new BigDecimal("0.0100"));
        nzd.setTermDepositRateOfSixMonth(new BigDecimal("0.0100"));
        nzd.setTermDepositRateOfOneYear(new BigDecimal("0.0100"));
        data.add(nzd);
        // 加拿大元
        FxCurrency cad = new FxCurrency();
        cad.setId(UuidTools.getUUID());
        cad.setChinaName("加拿大元");
        cad.setEnglishName("CAD");
        cad.setCountry("加拿大");
        cad.setDepositInterestRate(new BigDecimal("0.0100"));
        cad.setTermDepositRateOfOneMonth(new BigDecimal("0.0500"));
        cad.setTermDepositRateOfThreeMonth(new BigDecimal("0.0500"));
        cad.setTermDepositRateOfSixMonth(new BigDecimal("0.3000"));
        cad.setTermDepositRateOfOneYear(new BigDecimal("0.4000"));
        data.add(cad);
        // 港币
        FxCurrency hkd = new FxCurrency();
        hkd.setId(UuidTools.getUUID());
        hkd.setChinaName("港币");
        hkd.setEnglishName("HKD");
        hkd.setCountry("中国香港");
        hkd.setDepositInterestRate(new BigDecimal("0.0100"));
        hkd.setTermDepositRateOfOneMonth(new BigDecimal("0.0100"));
        hkd.setTermDepositRateOfThreeMonth(new BigDecimal("0.2500"));
        hkd.setTermDepositRateOfSixMonth(new BigDecimal("0.5000"));
        hkd.setTermDepositRateOfOneYear(new BigDecimal("0.7000"));
        data.add(hkd);
        // 日元
        FxCurrency jpy = new FxCurrency();
        jpy.setId(UuidTools.getUUID());
        jpy.setChinaName("日元");
        jpy.setEnglishName("JPY");
        jpy.setCountry("日本");
        jpy.setDepositInterestRate(new BigDecimal("0.0001"));
        jpy.setTermDepositRateOfOneMonth(new BigDecimal("0.0100"));
        jpy.setTermDepositRateOfThreeMonth(new BigDecimal("0.0100"));
        jpy.setTermDepositRateOfSixMonth(new BigDecimal("0.0100"));
        jpy.setTermDepositRateOfOneYear(new BigDecimal("0.0100"));
        data.add(jpy);
        // 林吉特
        FxCurrency myr = new FxCurrency();
        myr.setId(UuidTools.getUUID());
        myr.setChinaName("林吉特");
        myr.setEnglishName("MYR");
        myr.setCountry("马来西亚");
        myr.setDepositInterestRate(new BigDecimal("0.0001"));
        data.add(myr);
        // 新加坡元
        FxCurrency sgd = new FxCurrency();
        sgd.setId(UuidTools.getUUID());
        sgd.setChinaName("新加坡元");
        sgd.setEnglishName("SGD");
        sgd.setCountry("新加坡");
        sgd.setDepositInterestRate(new BigDecimal("0.0001"));
        sgd.setTermDepositRateOfOneMonth(new BigDecimal("0.0100"));
        sgd.setTermDepositRateOfThreeMonth(new BigDecimal("0.0100"));
        sgd.setTermDepositRateOfSixMonth(new BigDecimal("0.0100"));
        sgd.setTermDepositRateOfOneYear(new BigDecimal("0.0100"));
        data.add(sgd);
        // 新台币
        FxCurrency twd = new FxCurrency();
        twd.setId(UuidTools.getUUID());
        twd.setChinaName("新台币");
        twd.setEnglishName("TWD");
        twd.setCountry("中国台湾");
        twd.setDepositInterestRate(new BigDecimal("0.300"));
        twd.setTermDepositRateOfOneMonth(new BigDecimal("1.150"));
        twd.setTermDepositRateOfThreeMonth(new BigDecimal("1.350"));
        twd.setTermDepositRateOfSixMonth(new BigDecimal("1.550"));
        twd.setTermDepositRateOfOneYear(new BigDecimal("1.750"));
        data.add(twd);
        // 瑞典克朗
        FxCurrency sek = new FxCurrency();
        sek.setId(UuidTools.getUUID());
        sek.setChinaName("瑞典克朗");
        sek.setEnglishName("SEK");
        sek.setCountry("瑞典");
        sek.setDepositInterestRate(new BigDecimal("0.0001"));
        sek.setTermDepositRateOfOneMonth(new BigDecimal("0.0001"));
        sek.setTermDepositRateOfThreeMonth(new BigDecimal("0.0001"));
        sek.setTermDepositRateOfSixMonth(new BigDecimal("0.0001"));
        sek.setTermDepositRateOfOneYear(new BigDecimal("0.0001"));
        data.add(sek);
        return data;
    }
}
