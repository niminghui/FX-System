package com.shiep.fxauth.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 14:26
 * @description: FX_CURRENCY_PAIRS表的实体类
 */
@Data
public class FxCurrencyPairs implements Serializable {
    private static final long serialVersionUID = 7967120031347022192L;

    private String id;

    private String basicCurrency;

    private String secondaryCurrency;
}
