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

    CNY_USD("CNYUSD", "人民币美元"),
    CNY_EUR("CNYEUR", "人民币欧元"),
    CNY_HKD("CNYHKD", "人民币港币"),
    CNY_JPY("CNYJPY", "人民币日元"),
    CNY_GBP("CNYGBP", "人民币英镑"),
    CNY_AUD("CNYAUD", "人民币澳大利亚元"),
    CNY_CAD("CNYCAD", "人民币加拿大元"),
    CNY_SGD("CNYSGD", "人民币新加坡元"),
    CNY_NZD("CNYNZD", "人民币新西兰元"),
    CNY_SEK("CNYSEK", "人民币瑞典克朗"),
    CNY_MYR("CNYMYR", "人民币林吉特");

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
