package com.hospitalsearch.service.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator implements ConstraintValidator<Date,LocalDate> {

    @Override
    public void initialize(Date date) {

    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if(date==null){
            return true;
        }
        Pattern p = Pattern.compile("([12][980]\\d\\d)-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])");
        Matcher m = p.matcher(date.toString());
        return m.matches();
    }
}
