package com.hospitalsearch.controller;

import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.service.ManagerService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by igortsapyak on 24.05.16.
 */
@Controller
public class ManagerController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private UserService userService;


    @Autowired
    private ManagerService managerService;


    @RequestMapping(value = "/manageDoctors", method = RequestMethod.GET)
    public String getDoctorsByManager(Map<String, Object> model) {
        model.put("doctors", managerService.getDoctorsByManager());
        return "manageDoctors";
    }

    @RequestMapping(value = "/editHospitalsManagers", method = RequestMethod.GET)
    public String getManagersAndHospitals(Map<String, Object> model) {
        model.put("hospitals", hospitalService.getAll());
        model.put("users", userService.getByRole("MANAGER"));
        return "editHospitalsManagers";
    }

    @RequestMapping(value = "/applyManager", method = RequestMethod.POST)
    public String applyManager(@RequestBody Map<String, Long> hospitalData) {
        managerService.applyManager(hospitalData);
        return "redirect:/editHospitalsManagers";
    }

    @RequestMapping(value = "/deleteManager", method = RequestMethod.POST)
    public String deleteManager(@RequestParam Long hospitalId) {
        managerService.deleteHospitalManager(hospitalId);
        return "redirect: /editHospitalsManagers";

    }

    @RequestMapping(value = "/docs", method = RequestMethod.GET)
    public String docs(Map<String, Object> model){
        model.put("doctors", userService.getByRole("DOCTOR"));
        return "doctorsTwo";
    }


}
