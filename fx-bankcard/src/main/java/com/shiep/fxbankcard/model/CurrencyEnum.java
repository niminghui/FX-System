package com.shiep.fxbankcard.model;

import lombok.Getter;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 13:46
 * @description: 货币枚举类
 */
@Getter
public enum CurrencyEnum {
    /**
     * description: 银行目前可支持的13种货币
     */
    CNY("CNY","人民币"),
    AUD("AUD","澳大利亚元"),
    USD("USD","美元"),
    EUR("EUR","欧元"),
    GBP("GBP","英镑"),
    NZD("NZD","新西兰元"),
    CAD("CAD","加拿大元"),
    HKD("HKD","港币"),
    JPY("JPY","日元"),
    MYR("MYR","林吉特"),
    SGD("SGD","新加坡元"),
    TWD("TWD","新台币"),
    SEK("SEK","瑞典克朗");

    /**
     * description: 标准货币符号
     */
    private String code;
    /**
     * description: 货币中文名称
     */
    private String name;

    /**
     * description: 构造方法
     *
     * @param code 标准货币符号
     * @param name 货币中文名称
     * @return CurrencyEnum
     */
    CurrencyEnum(String code,String name){
        this.code=code;
        this.name=name;
    }

    /**
     * description: 通过code返回CurrencyEnum
     *
     * @param code 标准货币符号
     * @return com.shiep.fxbankcard.model.CurrencyEnum
     */
    public static CurrencyEnum parse(String code){
        CurrencyEnum[] values = values();
        for (CurrencyEnum value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        throw new RuntimeException("Unknown code of CurrencyEnum");
    }

    /**
     * description: 通过code返回name
     *
     * @param code 标准货币符号
     * @return java.lang.String
     */
    public static String getNameByCode(String code){
        return parse(code).getName();
    }

    /**
     * description: 通过name返回code
     *
     * @param name
     * @return java.lang.String
     */
    public static String getCodeByName(String name){
        CurrencyEnum[] values = values();
        for (CurrencyEnum value : values) {
            if(value.getName().equals(name)){
                return value.getCode();
            }
        }
        throw new RuntimeException("Unknown name of CurrencyEnum");
    }
}
