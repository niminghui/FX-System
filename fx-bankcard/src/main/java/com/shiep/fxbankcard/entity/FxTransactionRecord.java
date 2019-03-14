package com.shiep.fxbankcard.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 14:12
 * @description: FX_TRANSACTION_RECORD表的实体类
 */
@Data
@Entity
@Table(name = "FX_TRANSACTION_RECORD")
public class FxTransactionRecord implements Serializable {
    private static final long serialVersionUID = 3659338967022829019L;

    @Id
    @Column(name = "TRANSACTION_ID")
    private String id;

    @Column(name = "BANKCARD_ID",nullable = false)
    private String bankcardId;

    @Column(name = "TRANSACTION_MONEY",nullable = false)
    private BigDecimal money;

    @Column(name = "TRANSACTION_TYPE",nullable = false)
    private Integer type;

    @Column(name = "CURRENCY_EN_NAME",nullable = false)
    private String currencyEnglishName;

    @Column(name = "TRANSACTION_TIME",nullable = false)
    private Timestamp transactionTime;

    @Column(name = "TRANSACTION_PLACE")
    private String transactionPlace;

    @Column(name = "TRANSACTION_PEOPLE",nullable = false)
    private String transactionPeople;
}
