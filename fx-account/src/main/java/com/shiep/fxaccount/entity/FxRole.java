package com.shiep.fxaccount.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 10:26
 * @description: FX_ROLE表的实体类
 */
@Data
@Entity
@Table(name = "FX_ROLE")
public class FxRole implements Serializable {
    private static final long serialVersionUID = 6718331395784080381L;

    @Id
    @Column(name = "ROLE_ID")
    private String id;

    @Column(name = "ROLE_NAME",nullable = false,unique = true)
    private String name;

    @Column(name = "ROLE_DESCRIBE")
    private String describe;
}
