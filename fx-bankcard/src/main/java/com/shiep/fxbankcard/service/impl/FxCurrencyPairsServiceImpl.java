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

        // 欧元人民币
        FxCurrencyPairs eur_cny = new FxCurrencyPairs();
        eur_cny.setId(UuidTools.getUUID());
        eur_cny.setBasicCurrency("EUR");
        eur_cny.setSecondaryCurrency("CNY");
        data.add(eur_cny);
        // 港币人民币
        FxCurrencyPairs hkd_cny = new FxCurrencyPairs();
        hkd_cny.setId(UuidTools.getUUID());
        hkd_cny.setBasicCurrency("HKD");
        hkd_cny.setSecondaryCurrency("CNY");
        data.add(hkd_cny);
        // 日元人民币
        FxCurrencyPairs jpy_cny = new FxCurrencyPairs();
        jpy_cny.setId(UuidTools.getUUID());
        jpy_cny.setBasicCurrency("JPY");
        jpy_cny.setSecondaryCurrency("CNY");
        data.add(jpy_cny);
        // 英镑人民币
        FxCurrencyPairs gbp_cny = new FxCurrencyPairs();
        gbp_cny.setId(UuidTools.getUUID());
        gbp_cny.setBasicCurrency("GBP");
        gbp_cny.setSecondaryCurrency("CNY");
        data.add(gbp_cny);
        // 澳大利亚元人民币
        FxCurrencyPairs aud_cny = new FxCurrencyPairs();
        aud_cny.setId(UuidTools.getUUID());
        aud_cny.setBasicCurrency("AUD");
        aud_cny.setSecondaryCurrency("CNY");
        data.add(aud_cny);
        // 加拿大元人民币
        FxCurrencyPairs cad_cny = new FxCurrencyPairs();
        cad_cny.setId(UuidTools.getUUID());
        cad_cny.setBasicCurrency("CAD");
        cad_cny.setSecondaryCurrency("CNY");
        data.add(cad_cny);
        // 新加坡元人民币
        FxCurrencyPairs sgd_cny = new FxCurrencyPairs();
        sgd_cny.setId(UuidTools.getUUID());
        sgd_cny.setBasicCurrency("SGD");
        sgd_cny.setSecondaryCurrency("CNY");
        data.add(sgd_cny);
        // 新西兰元人民币
        FxCurrencyPairs nzd_cny = new FxCurrencyPairs();
        nzd_cny.setId(UuidTools.getUUID());
        nzd_cny.setBasicCurrency("NZD");
        nzd_cny.setSecondaryCurrency("CNY");
        data.add(nzd_cny);
        // 瑞典克朗人民币
        FxCurrencyPairs sek_cny = new FxCurrencyPairs();
        sek_cny.setId(UuidTools.getUUID());
        sek_cny.setBasicCurrency("SEK");
        sek_cny.setSecondaryCurrency("CNY");
        data.add(sek_cny);
        // 林吉特人民币
        FxCurrencyPairs myr_cny = new FxCurrencyPairs();
        myr_cny.setId(UuidTools.getUUID());
        myr_cny.setBasicCurrency("MYR");
        myr_cny.setSecondaryCurrency("CNY");
        data.add(myr_cny);
        return data;
    }
}
