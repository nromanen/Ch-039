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
    public String getWorkScheduler(@RequestParam("id") String id) {
        return workSchedulerService.getWorkScheduler(Long.parseLong(id));
    }




    @RequestMapping(value = "/**/supplyWorkScheduler", method = RequestMethod.POST)
    String supplyScheduler(@RequestBody String data, @RequestParam String doctorId) {

        System.out.println(data);
                workSchedulerService.updateWorkScheduler(doctorId, data);
        return "redirect:/";
    }

}
