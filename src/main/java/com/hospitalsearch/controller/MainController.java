package com.hospitalsearch.controller;

import com.hospitalsearch.service.PatientService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
@Controller
public class MainController {

    @Autowired
    private UserService service;

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Map<String,Object> model){
        return  "layout";
    }

    @RequestMapping(value = "/next",method = RequestMethod.GET)
    public String next(Map<String,Object> model){

        return  "next";
    }


}
