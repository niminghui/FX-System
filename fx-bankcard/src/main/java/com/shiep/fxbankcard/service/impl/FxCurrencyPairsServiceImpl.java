package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxCurrencyPairs;
import com.shiep.fxbankcard.repository.FxCurrencyPairsRepository;
import com.shiep.fxbankcard.service.IFxCurrencyPairsService;
import com.shiep.fxbankcard.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 15:48
 * @description: IFxCurrencyPairsService的实现类
 */
@Service
public class FxCurrencyPairsServiceImpl implements IFxCurrencyPairsService {
    @Autowired
    private FxCurrencyPairsRepository currencyPairsRepository;

    @Override
    public List<FxCurrencyPairs> init() {
        return currencyPairsRepository.saveAll(getInitData());
    }

    @Override
    public FxCurrencyPairs create(FxCurrencyPairs currencyPairs) {
        if (get(currencyPairs.getBasicCurrency(), currencyPairs.getSecondaryCurrency()) != null) {
            currencyPairs.setId(UuidTools.getUUID());
            return currencyPairsRepository.save(currencyPairs);
        }
        return null;
    }

    @Override
    public FxCurrencyPairs delete(String basicCurrency, String secondaryCurrency) {
        FxCurrencyPairs currencyPairs = get(basicCurrency, secondaryCurrency);
        if (currencyPairs != null) {
            currencyPairsRepository.delete(currencyPairs);
            return currencyPairs;
        }
        return null;
    }

    @Override
    public FxCurrencyPairs get(String basicCurrency, String secondaryCurrency) {
        return currencyPairsRepository.findByBasicCurrencyAndSecondaryCurrency(basicCurrency, secondaryCurrency);
    }

    @Override
    public List<FxCurrencyPairs> getAll() {
        return currencyPairsRepository.findAll();
    }

    /**
     * description: 返回初始货币对数据
     *
     * @param
     * @return java.util.List<com.shiep.fxbankcard.entity.FxCurrencyPairs>
     */
    private List<FxCurrencyPairs> getInitData() {
        List<FxCurrencyPairs> data = new ArrayList<>();
        // 澳元美元
        FxCurrencyPairs aud_usd = new FxCurrencyPairs();
        aud_usd.setId(UuidTools.getUUID());
        aud_usd.setBasicCurrency("AUD");
        aud_usd.setSecondaryCurrency("USD");
        data.add(aud_usd);
        // 欧元美元
        FxCurrencyPairs eur_usd = new FxCurrencyPairs();
        eur_usd.setId(UuidTools.getUUID());
        eur_usd.setBasicCurrency("EUR");
        eur_usd.setSecondaryCurrency("USD");
        data.add(eur_usd);
        // 英镑美元
        FxCurrencyPairs gbp_usd = new FxCurrencyPairs();
        gbp_usd.setId(UuidTools.getUUID());
        gbp_usd.setBasicCurrency("GBP");
        gbp_usd.setSecondaryCurrency("USD");
        data.add(gbp_usd);
        // 新西兰元美元
        FxCurrencyPairs nzd_usd = new FxCurrencyPairs();
        nzd_usd.setId(UuidTools.getUUID());
        nzd_usd.setBasicCurrency("NZD");
        nzd_usd.setSecondaryCurrency("USD");
        data.add(nzd_usd);
        // 美元加拿大元
        FxCurrencyPairs usd_cad = new FxCurrencyPairs();
        usd_cad.setId(UuidTools.getUUID());
        usd_cad.setBasicCurrency("USD");
        usd_cad.setSecondaryCurrency("CAD");
        data.add(usd_cad);
        // 美元人民币
        FxCurrencyPairs usd_cny = new FxCurrencyPairs();
        usd_cny.setId(UuidTools.getUUID());
        usd_cny.setBasicCurrency("USD");
        usd_cny.setSecondaryCurrency("CNY");
        data.add(usd_cny);
        // 美元港元
        FxCurrencyPairs usd_hkd = new FxCurrencyPairs();
        usd_hkd.setId(UuidTools.getUUID());
        usd_hkd.setBasicCurrency("USD");
        usd_hkd.setSecondaryCurrency("HKD");
        data.add(usd_hkd);
        // 美元日元
        FxCurrencyPairs usd_jpy = new FxCurrencyPairs();
        usd_jpy.setId(UuidTools.getUUID());
        usd_jpy.setBasicCurrency("USD");
        usd_jpy.setSecondaryCurrency("JPY");
        data.add(usd_jpy);
        // 美元林吉特
        FxCurrencyPairs usd_myr = new FxCurrencyPairs();
        usd_myr.setId(UuidTools.getUUID());
        usd_myr.setBasicCurrency("USD");
        usd_myr.setSecondaryCurrency("MYR");
        data.add(usd_myr);
        // 美元新加坡元
        FxCurrencyPairs usd_sgd = new FxCurrencyPairs();
        usd_sgd.setId(UuidTools.getUUID());
        usd_sgd.setBasicCurrency("USD");
        usd_sgd.setSecondaryCurrency("SGD");
        data.add(usd_sgd);
        // 美元台币
        FxCurrencyPairs usd_twd = new FxCurrencyPairs();
        usd_twd.setId(UuidTools.getUUID());
        usd_twd.setBasicCurrency("USD");
        usd_twd.setSecondaryCurrency("TWD");
        data.add(usd_twd);
        // 人民币美元
        FxCurrencyPairs cny_usd = new FxCurrencyPairs();
        cny_usd.setId(UuidTools.getUUID());
        cny_usd.setBasicCurrency("CNY");
        cny_usd.setSecondaryCurrency("USD");
        data.add(cny_usd);
        // 人民币欧元
        FxCurrencyPairs cny_eur = new FxCurrencyPairs();
        cny_eur.setId(UuidTools.getUUID());
        cny_eur.setBasicCurrency("CNY");
        cny_eur.setSecondaryCurrency("EUR");
        data.add(cny_eur);
        // 人民币港币
        FxCurrencyPairs cny_hkd = new FxCurrencyPairs();
        cny_hkd.setId(UuidTools.getUUID());
        cny_hkd.setBasicCurrency("CNY");
        cny_hkd.setSecondaryCurrency("HKD");
        data.add(cny_hkd);
        // 人民币日元
        FxCurrencyPairs cny_jpy = new FxCurrencyPairs();
        cny_jpy.setId(UuidTools.getUUID());
        cny_jpy.setBasicCurrency("CNY");
        cny_jpy.setSecondaryCurrency("JPY");
        data.add(cny_jpy);
        // 人民币英镑
        FxCurrencyPairs cny_gbp = new FxCurrencyPairs();
        cny_gbp.setId(UuidTools.getUUID());
        cny_gbp.setBasicCurrency("CNY");
        cny_gbp.setSecondaryCurrency("GBP");
        data.add(cny_gbp);
        // 人民币澳大利亚元
        FxCurrencyPairs cny_aud = new FxCurrencyPairs();
        cny_aud.setId(UuidTools.getUUID());
        cny_aud.setBasicCurrency("CNY");
        cny_aud.setSecondaryCurrency("AUD");
        data.add(cny_aud);
        // 人民币加拿大元
        FxCurrencyPairs cny_cad = new FxCurrencyPairs();
        cny_cad.setId(UuidTools.getUUID());
        cny_cad.setBasicCurrency("CNY");
        cny_cad.setSecondaryCurrency("CAD");
        data.add(cny_cad);
        // 人民币新加坡元
        FxCurrencyPairs cny_sgd = new FxCurrencyPairs();
        cny_sgd.setId(UuidTools.getUUID());
        cny_sgd.setBasicCurrency("CNY");
        cny_sgd.setSecondaryCurrency("SGD");
        data.add(cny_sgd);
        // 人民币新西兰元
        FxCurrencyPairs cny_nzd = new FxCurrencyPairs();
        cny_nzd.setId(UuidTools.getUUID());
        cny_nzd.setBasicCurrency("CNY");
        cny_nzd.setSecondaryCurrency("NZD");
        data.add(cny_nzd);
        // 人民币瑞典克朗
        FxCurrencyPairs cny_sek = new FxCurrencyPairs();
        cny_sek.setId(UuidTools.getUUID());
        cny_sek.setBasicCurrency("CNY");
        cny_sek.setSecondaryCurrency("SEK");
        data.add(cny_sek);
        // 人民币林吉特
        FxCurrencyPairs cny_myr = new FxCurrencyPairs();
        cny_myr.setId(UuidTools.getUUID());
        cny_myr.setBasicCurrency("CNY");
        cny_myr.setSecondaryCurrency("MYR");
        data.add(cny_myr);
        return data;
    }
}
