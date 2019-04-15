package com.shiep.fxbankcard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 14:02
 * @description: FX_BANKCARD表的实体类
 */
@Data
@Entity
@Table(name = "FX_BANKCARD")
public class FxBankCard implements Serializable {
    private static final long serialVersionUID = -3172864426520396953L;

    @Id
    @Column(name = "BANKCARD_ID")
    private String id;

    @Column(name = "BANKCARD_PASSWORD", nullable = false)
    private String password;

    @Column(name = "BANKCARD_STATUS", nullable = false)
    private Integer status;

    @Column(name = "BANKCARD_CREATED_TIME", nullable = false)
    private Timestamp createdTime;

    @Column(name = "BANKCARD_CREATED_PLACE", nullable = false)
    private String createdPlace;

    @Column(name = "USER_ID", nullable = false, unique = true)
    private String userID;

    /**
     * description: 银行卡状态
     */
    @Transient
    public static final int INACTIVE = 0;
    @Transient
    public static final int ACTIVATED = 1;
    @Transient
    public static final int FREEZE = 2;
    @Transient
    public static final int CANCELLATION = 3;
}
