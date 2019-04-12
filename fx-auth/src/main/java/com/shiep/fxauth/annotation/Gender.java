package com.shiep.fxauth.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: 倪明辉
 * @date: 2019/4/12 10:25
 * @description: 自定义性别校验注解
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
@Documented
public @interface Gender {
    String message() default "性别参数值不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 允许值，若使用方的性别枚举不同，可以通过该字段指定校验通过的值。
    String[] allowed() default {"男", "女"};
}
