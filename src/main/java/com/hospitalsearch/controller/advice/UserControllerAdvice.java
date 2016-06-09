package com.hospitalsearch.controller.advice;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Andrew Jasinskiy on 03.06.16
 */

@ControllerAdvice
public class UserControllerAdvice {

    @Autowired
    private UserService userService;

    @ModelAttribute("userName")
    public User loggedUser() {
        return userService.getByEmail(PrincipalConverter.getPrincipal());
    }
}
