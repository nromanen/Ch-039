package com.hospitalsearch.controller;

import com.hospitalsearch.service.WorkSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by igortsapyak on 29.05.16.
 */
@Controller
public class WorkSchedulerController {

    @Autowired
    private WorkSchedulerService workSchedulerService;

    @ResponseBody
    @RequestMapping(value = "/**/getWorkScheduler", method = RequestMethod.GET)
    public String getWorkScheduler(@RequestParam("id") Long id) {
        return workSchedulerService.getByDoctorId(id).getWorkScheduler();
    }

    @ResponseBody
    @RequestMapping(value = "/getWorkSchedulerByPrincipal", method = RequestMethod.GET)
    public String getWorkSchedulerByDoctor(@RequestParam("doctor") String doctorEmail) {
        return workSchedulerService.getByDoctorEmail(doctorEmail);
    }

    @RequestMapping(value = "/**/supplyWorkScheduler", method = RequestMethod.POST)
    public String supplyScheduler(@RequestBody String data, @RequestParam Long doctorId) {
        workSchedulerService.saveWorkScheduler(data, doctorId);
        return "redirect:/";
    }

    @RequestMapping(value = "/workscheduler", method = RequestMethod.GET)
    public String getAppointments() {
        return "workscheduler";
    }


}
