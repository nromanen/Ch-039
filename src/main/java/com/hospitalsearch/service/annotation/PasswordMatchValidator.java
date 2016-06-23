package com.hospitalsearch.service.annotation;


import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Andrew Jasinskiy on 17.06.16
 */
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final PasswordMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean toReturn = false;
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
            toReturn = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception e) {
            System.out.println(e.toString());
        }
        //If the validation failed
        if (!toReturn) {
            context.disableDefaultConstraintViolation();
            //In the initialize method you get the errorMessage: constraintAnnotation.message();
            context.buildConstraintViolationWithTemplate(message).addNode(firstFieldName).addConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addNode(secondFieldName).addConstraintViolation();
        }
        return toReturn;
    }
}
