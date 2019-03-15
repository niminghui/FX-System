package com.shiep.fxbankcard.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 14:26
 * @description: FX_CURRENCY_PAIRS表的实体类
 */
@Data
@Entity
@Table(name = "FX_CURRENCY_PAIRS")
public class FxCurrencyPairs implements Serializable {
    private static final long serialVersionUID = 7967120031347022192L;

    @Id
    @Column(name = "CURRENCY_PAIRS_ID")
    private String id;

    @Column(name = "BASIC_CURRENCY", nullable = false)
    private String basicCurrency;

    @Column(name = "SECONDARY_CURRENCY", nullable = false)
    private String secondaryCurrency;
}
