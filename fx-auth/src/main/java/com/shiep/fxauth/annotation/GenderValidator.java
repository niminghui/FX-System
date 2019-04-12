package com.shiep.fxauth.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: 倪明辉
 * @date: 2019/4/12 10:25
 * @description: 性别校验类
 */
public class GenderValidator implements ConstraintValidator<Gender, String> {
    private Set<String> allowed = new HashSet<>(2);

    /**
     * description: 初始化允许校验通过的值
     *
     * @param gender 性别注解
     * @return void
     */
    @Override
    public void initialize(Gender gender) {
        for (String s : gender.allowed()) {
            allowed.add(s);
        }
    }

    /**
     * description: 执行校验
     *
     * @param value
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return allowed.contains(value);
    }
}
