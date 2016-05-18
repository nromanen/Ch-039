package com.hospitalsearch.controller;

import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.entity.WorkInterval;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.FeedbackService;
import com.hospitalsearch.service.UserDetailService;
import com.hospitalsearch.service.WorkIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
public class WorkIntervalController {

    @Autowired
    private WorkIntervalService workIntervalService;

    @Autowired
    private DoctorInfoService doctorInfoService;

    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private FeedbackService feedbackService;

    
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/**/getIntervals",method = RequestMethod.GET)
    public @ResponseBody
    List<WorkInterval> listAllWorkintervals(@RequestParam("id")String id) {
        return workIntervalService.getAllbyDoctorId(Long.parseLong(id));
    }

    @RequestMapping(value = "/hospital/{*}/department/{dep_id}/doctor/{d_id}/dashboard",method = RequestMethod.GET)
    String string(
    		@PathVariable("dep_id") Long departmentId,
    		@PathVariable("d_id") Long doctorId,
    		ModelMap model){
    	
    	
    	Department d =departmentService.getById(departmentId);
        model.put("department", d);
        model.put("hospital", d.getHospital());
        model.put("formatter", DateTimeFormatter.ofPattern("d MMM uuuu hh:mm"));
        model.put("feedbacks", feedbackService.getByDoctorId(doctorId));
        
    	
        UserDetail userDetail = userDetailService.getById(doctorId);
        model.addAttribute("id",userDetail.getDoctorsDetails().getId());
        model.addAttribute("doctor", userDetailService.getById(doctorId));
        return "start";
    }

    @RequestMapping(value = "/**/supplyIntervals", method = RequestMethod.POST)
    String eventProcessor(HttpServletRequest request, 
    		@RequestParam("id")Long id){
        workIntervalService.actionControl(request.getParameterMap(), id);
        return "redirect:/";

    }

//    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
//    public String patientCard(ModelMap model){
//        model.addAttribute("doctors", doctorInfoService.getAllDoctors());
//        return "/doctors";
//    }

}
