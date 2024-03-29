package com.shiep.fxauth.vo;


import com.shiep.fxauth.annotation.Password;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author: 倪明辉
 * @date: 2019/3/18 14:03
 * @description: 封装登录数据
 */
@Component
@Data
public class LoginVo {
    @NotNull(message = "验证码不能为空")
    @Pattern(regexp = "([0-9]{4})", message = "验证码只能由四位数字组成")
    private String userCaptcha;

    @NotNull(message = "账户名不能为空")
    @Size(min = 3, max = 25, message = "账户名长度介于3-25之间")
    private String accountName;

    @Password
    private String password;

    private Boolean rememberMe;
}
