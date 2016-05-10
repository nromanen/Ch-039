package com.hospitalsearch.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * Created by speedfire on 4/28/16.
 */

@Controller
public class MainController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Map<String,Object> model){
 
        return  "layout";
    }

    @RequestMapping(value = "/next",method = RequestMethod.GET)
    public String next(Map<String,Object> model){
 
        return  "next";
    }


}
