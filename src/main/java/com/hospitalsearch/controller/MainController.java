package com.hospitalsearch.controller;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.PatientService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Map<String,Object> model) throws Exception{
        return "layout";
    }

    @RequestMapping(value = "/next",method = RequestMethod.GET)
    public String next(Map<String,Object> model){
        return  "next";
    }

}
