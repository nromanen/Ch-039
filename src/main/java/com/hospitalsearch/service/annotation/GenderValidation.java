package com.hospitalsearch.service.annotation;

import com.hospitalsearch.util.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenderValidation implements ConstraintValidator<Gender,com.hospitalsearch.util.Gender> {
    @Override
    public void initialize(Gender gender) {
    }

    @Override
    public boolean isValid(com.hospitalsearch.util.Gender gender, ConstraintValidatorContext constraintValidatorContext) {
        if (gender==null){
            return true;
        }
        Pattern p = Pattern.compile("MAN|WOMAN");
        Matcher m = p.matcher(gender.toString());
        return m.matches();
    }
    }

