package com.hospitalsearch.service.annotation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class OneDayValidator implements ConstraintValidator<OneDay, LocalDate> {

    @Override
    public void initialize(OneDay oneDay) {

    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if(date==null){
            return true;
        }
        return date.plus(1, ChronoUnit.DAYS).isAfter(LocalDate.now());
    }
}
