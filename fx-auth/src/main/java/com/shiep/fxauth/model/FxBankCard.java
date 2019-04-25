package com.shiep.fxauth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 14:02
 * @description: FX_BANKCARD表的实体类
 */
@Data
public class FxBankCard implements Serializable {
    private static final long serialVersionUID = -3172864426520396953L;

    private String id;

    private String password;

    private Integer status;

    private Timestamp createdTime;

    private String createdPlace;

    private String userID;

    /**
     * description: 银行卡状态
     */
    @JsonIgnore
    public static final int INACTIVE = 0;
    @JsonIgnore
    public static final int ACTIVATED = 1;
    @JsonIgnore
    public static final int FREEZE = 2;
    @JsonIgnore
    public static final int CANCELLATION = 3;
}
