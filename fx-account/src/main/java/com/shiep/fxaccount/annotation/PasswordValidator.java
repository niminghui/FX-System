package com.shiep.fxaccount.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 倪明辉
 * @date: 2019/3/18 15:37
 * @description: 自定义密码校验规则
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {
    //5~30位的数字与字母组合
    private static Pattern pattern = Pattern.compile("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,30}");

    public void initialize(Password constraintAnnotation) {
        //do nothing
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        Matcher m = pattern.matcher(value);
        return m.matches();
    }
}

