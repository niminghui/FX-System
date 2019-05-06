package com.shiep.fxbankcard.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 14:15
 * @description: 货币对枚举类
 */
@Getter
public enum CurrencyPairsEnum {
    /**
     * description: 银行目前可支持的22种货币对
     */
    AUD_USD("AUDUSD", "澳元美元"),
    DINIW("DINIW", "美元指数"),
    EUR_USD("EURUSD", "欧元美元"),
    GBP_USD("GBPUSD", "英镑美元"),
    NZD_USD("NZDUSD", "新西兰元美元"),
    USD_CAD("USDCAD", "美元加拿大元"),
    USD_CNY("USDCNY", "美元人民币"),
    USD_HKD("USDHKD", "美元港元"),
    USD_JPY("USDJPY", "美元日元"),
    USD_MYR("USDMYR", "美元林吉特"),
    USD_SGD("USDSGD", "美元新加坡元"),
    USD_TWD("USDTWD", "美元台币"),

    CNY_USD("USDCNY", "美元人民币"),
    CNY_EUR("EURCNY", "欧元人民币"),
    CNY_HKD("HKDCNY", "港币人民币"),
    CNY_JPY("JPYCNY", "日元人民币"),
    CNY_GBP("GBPCNY", "英镑人民币"),
    CNY_AUD("AUDCNY", "澳大利亚元人民币"),
    CNY_CAD("CADCNY", "加拿大元人民币"),
    CNY_SGD("SGDCNY", "新加坡元人民币"),
    CNY_NZD("NZDCNY", "新西兰元人民币"),
    CNY_SEK("SEKCNY", "瑞典克朗人民币"),
    CNY_MYR("MYRCNY", "林吉特人民币");

    /**
     * description: 标准货币对符号
     */
    private String code;
    /**
     * description: 货币对中文名称
     */
    private String name;

    /**
     * description: 构造方法
     *
     * @param code 标准货币对符号
     * @param name 货币对中文名称
     * @return CurrencyPairsEnum
     */
    CurrencyPairsEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * description: 通过code返回CurrencyPairsEnum
     *
     * @param code 标准货币符号
     * @return com.shiep.fxbankcard.model.CurrencyPairsEnum
     */
    public static CurrencyPairsEnum parse(String code) {
        CurrencyPairsEnum[] values = values();
        for (CurrencyPairsEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new RuntimeException("Unknown code of CurrencyPairsEnum");
    }

    /**
     * description: 通过code返回name
     *
     * @param code 标准货币对符号
     * @return java.lang.String
     */
    public static String getNameByCode(String code) {
        return parse(code).getName();
    }

    /**
     * description: 返回所有的货币对标准符号
     *
     * @param
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getAllCode() {
        List<String> result = new ArrayList<>(50);
        CurrencyPairsEnum[] values = values();
        for (CurrencyPairsEnum value : values) {
            result.add(value.getCode());
        }
        return result;
    }
}
