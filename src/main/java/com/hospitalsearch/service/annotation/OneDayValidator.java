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

        LocalDate dateTime = date.toLocalDateTime().toLocalDate();
        return dateTime.isEqual(LocalDate.now());

    }


}
