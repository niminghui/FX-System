package com.shiep.fxauth.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: 倪明辉
 * @date: 2019/3/18 15:36
 * @description: 自定义密码校验注解
 */

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "密码必须是5~30位数字和字母组合";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}