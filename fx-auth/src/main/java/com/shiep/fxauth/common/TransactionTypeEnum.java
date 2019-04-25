package com.shiep.fxauth.common;

import lombok.Getter;

/**
 * @author: 倪明辉
 * @date: 2019/4/23 10:36
 * @description: 交易类型枚举类
 */
@Getter
public enum TransactionTypeEnum {
    /**
     * description: 5种交易类型
     */
    TRANSFER_EXPENDITURE(0, "转账支出"),
    EXCHANGE_EXPENDITURE(1, "外汇兑换支出"),
    TRANSFER_INTO(2, "转账存入"),
    EXCHANGE_INTO(3, "外汇兑换存入"),
    DEPOSIT_INTO(4, "存款利息存入");

    /**
     * description: 交易类型码
     */
    private Integer code;

    /**
     * description: 交易类型描述
     */
    private String name;

    /**
     * description: 构造方法
     *
     * @param code 交易类型码
     * @param name 交易类型描述
     * @return
     */
    TransactionTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * description: 通过code返回TransactionTypeEnum
     *
     * @param code 交易类型码
     * @return com.shiep.fxbankcard.model.TransactionTypeEnum
     */
    public static TransactionTypeEnum parse(Integer code) {
        TransactionTypeEnum[] values = values();
        for (TransactionTypeEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new RuntimeException("Unknown code of CurrencyEnum");
    }
}
