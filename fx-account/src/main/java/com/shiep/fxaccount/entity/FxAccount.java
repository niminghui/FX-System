package com.shiep.fxaccount.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 10:20
 * @description: FX_ACCOUNT表的实体类
 */
@Data
@Entity
@Table(name = "FX_ACCOUNT")
public class FxAccount implements Serializable {
    private static final long serialVersionUID = -309418584051632313L;

    @Id
    @Column(name = "ACCOUNT_ID")
    private String id;

    @Column(name = "ACCOUNT_NAME",nullable = false,unique = true)
    private String name;

    @Column(name = "ACCOUNT_PASSWORD",nullable = false)
    private String password;

    @Column(name = "BANKCARD_ID",nullable = false,unique = true)
    private String bankCardId;
}
