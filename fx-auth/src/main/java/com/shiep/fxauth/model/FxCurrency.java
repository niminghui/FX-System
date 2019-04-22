package com.shiep.fxauth.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 14:18
 * @description: FX_CURRENCY表的实体类
 */
@Data
public class FxCurrency implements Serializable {
    private static final long serialVersionUID = 5776433053907979970L;

    private String id;

    private String chinaName;

    private String englishName;

    private String country;

    private BigDecimal depositInterestRate;

    private BigDecimal termDepositRateOfOneMonth;

    private BigDecimal termDepositRateOfThreeMonth;

    private BigDecimal termDepositRateOfSixMonth;

    private BigDecimal termDepositRateOfOneYear;
}
