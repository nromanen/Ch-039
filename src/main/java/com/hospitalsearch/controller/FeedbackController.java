package com.hospitalsearch.controller;

import com.hospitalsearch.service.PatientService;
import com.hospitalsearch.service.UserDetailService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class FeedbackController {
    @Autowired
    private PatientService service;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService detailService;

    @RequestMapping("/hospital/doctor/{id}")
    public String profile(Map<String,Object> model, @PathVariable Integer id){
        return "profile";
    }
}
