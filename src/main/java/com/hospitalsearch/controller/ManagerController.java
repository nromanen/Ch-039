package com.hospitalsearch.controller;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dao.impl.DoctorInfoDAOImpl;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by igortsapyak on 24.05.16.
 */
@Controller
public class ManagerController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DoctorInfoDAOImpl doctorInfoDAO;


    @RequestMapping(value = "/hospitalManager", method = RequestMethod.GET)
    String eventProcessor(Map<String,Object> model){
//        System.out.println(doctorInfoDAO.findByManagerId(userDAO.getByEmail(PrincipalConverter.getPrincipal()).getId()));
        model.put("doctors", doctorInfoDAO.findByManagerId(userDAO.getByEmail(PrincipalConverter.getPrincipal()).getId()));
        return "manager";
    }

}
