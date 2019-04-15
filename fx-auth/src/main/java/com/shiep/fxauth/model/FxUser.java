package com.shiep.fxauth.model;

import com.shiep.fxauth.vo.UserInfoVo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 13:44
 * @description: FX_USER_INFO表的实体类
 */
@Data
public class FxUser implements Serializable {
    private static final long serialVersionUID = 6627296257948204490L;

    private String id;
    private String chinaName;
    private String englishName;
    private String gender;
    private String idCardNum;
    private byte[] faceImage;
    private String province;
    private String city;
    private String region;
    private String email;
    private String phone;

    public FxUser(UserInfoVo userInfoVo) {
        this.chinaName = userInfoVo.getChineseName();
        this.englishName = userInfoVo.getEnglishName();
        this.gender = userInfoVo.getGender();
        this.idCardNum = userInfoVo.getIdNumber();
        this.province = userInfoVo.getProvince();
        this.city = userInfoVo.getCity();
        this.region = userInfoVo.getCountry();
        this.email = userInfoVo.getEmail();
        this.phone = userInfoVo.getPhone();
    }

    public FxUser() {
        super();
    }
}
