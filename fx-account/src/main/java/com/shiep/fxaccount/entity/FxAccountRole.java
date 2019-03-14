package com.shiep.fxaccount.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 10:32
 * @description: FX_ACCOUNT_ROLE表的实体类
 */
@Data
@Entity
@Table(name = "FX_ACCOUNT_ROLE")
public class FxAccountRole implements Serializable {
    private static final long serialVersionUID = -1357496046063309516L;

    @Id
    @Column(name = "ACCOUNT_ROLE_ID")
    private String id;

    @Column(name = "ACCOUNT_NAME",nullable = false)
    private String accountName;

    @Column(name = "ROLE_NAME",nullable = false)
    private String roleName;

    @Column(name = "CREATED_TIME",nullable = false)
    private Timestamp createdTime;
}
