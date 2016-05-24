package com.hospitalsearch.service.annotation;

import com.hospitalsearch.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userDAO.emailExists(value);
    }
}
