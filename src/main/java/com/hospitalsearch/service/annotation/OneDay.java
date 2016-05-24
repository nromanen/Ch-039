package com.hospitalsearch.service.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {OneDayValidator.class}
)
public @interface OneDay {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



}
