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
 * @date: 2019/3/14 14:07
 * @description: FX_ASSET表的实体类
 */
@Data
@Entity
@Table(name = "FX_ASSET")
public class FxAsset implements Serializable {
    private static final long serialVersionUID = 2523830998163742083L;

    @Id
    @Column(name = "ASSET_ID")
    private String id;

    @Column(name = "CURRENCY_EN_NAME",nullable = false)
    private String currencyCode;

    @Column(name = "ASSET_BALANCE",nullable = false)
    private BigDecimal balance;

    @Column(name = "BANKCARD_ID",nullable = false)
    private String bankcardId;
}
