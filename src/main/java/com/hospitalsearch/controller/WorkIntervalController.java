package com.hospitalsearch.controller;

import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.entity.WorkInterval;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.UserDetailService;
import com.hospitalsearch.service.WorkIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class WorkIntervalController {

    @Autowired
    private WorkIntervalService workIntervalService;

    @Autowired
    private DoctorInfoService doctorInfoService;

    @Autowired
    private UserDetailService userDetailService;


    @RequestMapping(value = "/123",method = RequestMethod.GET)
    public @ResponseBody
    List<WorkInterval> listAllWorkintervals(@RequestParam("id")String id) {
        return workIntervalService.getAllbyDoctorId(Long.parseLong(id));
    }

    @RequestMapping(value = "/start",method = RequestMethod.GET)
    String string(@RequestParam("id")String id, ModelMap model){
        UserDetail userDetail = userDetailService.getById(Long.parseLong(id));
        model.addAttribute("id",userDetail.getDoctorsDetails().getId());
        model.addAttribute("doctor", userDetailService.getById(Long.parseLong(id)));
        return "start";
    }

    @RequestMapping(value = "/321", method = RequestMethod.POST)
    String eventProcessor(HttpServletRequest request, @RequestParam("id")String id){
        workIntervalService.actionControl(request.getParameterMap(), Long.parseLong(id));
        return "redirect:/";

    }

//    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
//    public String patientCard(ModelMap model){
//        model.addAttribute("doctors", doctorInfoService.getAllDoctors());
//        return "/doctors";
//    }

}
