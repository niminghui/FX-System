package com.shiep.fxauth.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 14:07
 * @description: FX_ASSET表的实体类
 */
@Data
public class FxAsset implements Serializable {
    private static final long serialVersionUID = 2523830998163742083L;

    private String id;

    private String currencyCode;

    private BigDecimal balance;

    private String bankcardId;

    private Integer version;
}
