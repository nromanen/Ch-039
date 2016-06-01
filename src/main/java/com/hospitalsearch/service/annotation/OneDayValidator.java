package com.hospitalsearch.service.annotation;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class OneDayValidator implements ConstraintValidator<OneDay, Timestamp> {

    @Override
    public void initialize(OneDay oneDay) {

    }

    @Override
    public boolean isValid(Timestamp date, ConstraintValidatorContext constraintValidatorContext) {
        if(date==null){
            return true;
        }
        if(date==null){
            return true;
        }
        LocalDateTime dateTime = date.toLocalDateTime();
        return dateTime.plus(8, ChronoUnit.HOURS).isAfter(LocalDateTime.now());
    }


}
