package com.shiep.fxbankcard.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 13:44
 * @description: FX_USER_INFO表的实体类
 */
@Data
@Entity
@Table(name = "FX_USER_INFO")
public class FxUser implements Serializable {
    private static final long serialVersionUID = 6627296257948204490L;

    @Id
    @Column(name = "USER_ID")
    private String id;

    @Column(name = "USER_CN_NAME", nullable = false)
    private String chinaName;

    @Column(name = "USER_EN_NAME")
    private String englishName;

    @Column(name = "USER_GENDER", nullable = false)
    private String gender;

    @Column(name = "USER_IDENTITY_CARD", nullable = false, unique = true)
    private String idCardNum;

    @Column(name = "USER_IMAGE")
    private byte[] faceImage;

    @Column(name = "USER_PROV", nullable = false)
    private String province;

    @Column(name = "USER_CITY", nullable = false)
    private String city;

    @Column(name = "USER_REGION", nullable = false)
    private String region;

    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USER_PHONE")
    private String phone;
}
