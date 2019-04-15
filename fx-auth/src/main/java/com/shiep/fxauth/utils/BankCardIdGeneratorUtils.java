package com.shiep.fxauth.utils;

import java.util.Random;

/**
 * @author: 倪明辉
 * @date: 2019/4/15 9:34
 * @description: 随机生成(中国)银行卡号码工具类
 * 借记卡卡号长度为19位，编码规则如下：1-6是卡BIN号；7-10是地区码；11-18是顺序号，系统自动生成,无重复；19位是校验位。
 */
public class BankCardIdGeneratorUtils {
    /**
     * description: 银行卡BIN号
     */
    private static final String ID_PREFIX = "621660";

    /**
     * description: 默认是上海地区码（优化：通过IP取地址，在转换成地址码）
     */
    private static final String AREA_CODE = "2900";

    /**
     * description: 生成19位中国银行卡号码
     *
     * @param
     * @return java.lang.String
     */
    public static String getBankCardID() {
        StringBuffer buffer = new StringBuffer();
        // 1-6位是卡BIN号
        buffer.append(ID_PREFIX);
        // 7-10位是地区码
        buffer.append(AREA_CODE);
        // 11-18位是顺序号
        buffer.append(getRandomDigit(8));
        // 19位是校验位
        buffer.append(getRandomDigit(1));
        return buffer.toString();
    }

    /**
     * description: 生成8位的随机顺序号
     *
     * @param
     * @return java.lang.String
     */
    private static String getRandomDigit(int bit) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < bit; i++) {
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
    }
}