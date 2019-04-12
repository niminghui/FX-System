package com.shiep.fxauth.vo;

import com.shiep.fxauth.annotation.Gender;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author: 倪明辉
 * @date: 2019/4/12 10:13
 * @description: 用户信息视图类
 */
@Data
@Component
public class UserInfoVo {
    @NotNull(message = "中文名不能为空")
    private String chineseName;

    private String englishName;

    @Gender
    private String gender;

    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)",
            message = "身份证号码格式错误")
    private String idNumber;

    @NotNull(message = "出生省份不能为空")
    private String province;

    @NotNull(message = "出生城市不能为空")
    private String city;

    @NotNull(message = "出生区县不能为空")
    private String country;

    @Email
    private String email;

    @Pattern(regexp = "(^1(3|4|5|7|8)\\d{9}$)", message = "手机号码格式错误")
    private String phone;
}
