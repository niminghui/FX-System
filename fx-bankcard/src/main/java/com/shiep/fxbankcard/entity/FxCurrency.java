package com.shiep.fxbankcard.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 14:18
 * @description: FX_CURRENCY表的实体类
 */
@Data
@Entity
@Table(name = "FX_CURRENCY")
public class FxCurrency implements Serializable {
    private static final long serialVersionUID = 5776433053907979970L;

    @Id
    @Column(name = "CURRENCY_ID")
    private String id;

    @Column(name = "CURRENCY_CN_NAME", nullable = false, unique = true)
    private String chinaName;
    ;

    @Column(name = "CURRENCY_EN_NAME", nullable = false, unique = true)
    private String englishName;

    @Column(name = "CURRENCY_COUNTRY", nullable = false)
    private String country;

    @Column(name = "CURRENCY_DIR", nullable = false)
    private BigDecimal depositInterestRate;

    @Column(name = "CURRENCY_TDR_1")
    private BigDecimal termDepositRateOfOneMonth;

    @Column(name = "CURRENCY_TDR_3")
    private BigDecimal termDepositRateOfThreeMonth;

    @Column(name = "CURRENCY_TDR_6")
    private BigDecimal termDepositRateOfSixMonth;

    @Column(name = "CURRENCY_TDR_12")
    private BigDecimal termDepositRateOfOneYear;
}
