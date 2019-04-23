package com.shiep.fxauth.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 14:12
 * @description: FX_TRANSACTION_RECORD表的实体类
 */
@Data
public class FxTransactionRecord implements Serializable {
    private static final long serialVersionUID = 3659338967022829019L;

    private String id;

    private String bankcardId;

    private BigDecimal money;

    private Integer type;

    private String currencyCode;

    private Timestamp transactionTime;

    private String transactionPlace;

    private String transactionPeople;
}
