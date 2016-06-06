package com.hospitalsearch.controller;

import com.hospitalsearch.dao.impl.DoctorInfoDAOImpl;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorInfoService doctorInfoService;

    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private DoctorInfoDAOImpl doctorInfoDAO;

    @Autowired
    private DepartmentService departmentService;


    @ResponseBody
    @RequestMapping(value = "/**/getAppointments",method = RequestMethod.GET)
    public List<Appointment> listAllAppointments(@RequestParam("id")String id) {
        return appointmentService.getAllbyDoctorId(Long.parseLong(id));
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

    @RequestMapping(value = "/doctor/{d_id}/manage",method = RequestMethod.GET)
    String stringtwo(

            @PathVariable("d_id") Long doctorId,
            ModelMap model){

        UserDetail userDetail = userDetailService.getById(doctorId);
        model.addAttribute("id",userDetail.getDoctorsDetails().getId());
        model.addAttribute("doctor", userDetailService.getById(doctorId));
        return "mange";
    }


    @RequestMapping(value = "/**/supplyIntervals", method = RequestMethod.POST)
    String eventProcessor(HttpServletRequest request,
    		@RequestParam("id")Long id) {
        appointmentService.actionControl(request.getParameterMap(), id);
        return "redirect:/";
    }




//    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
//    public String patientCard(ModelMap model){
//        model.addAttribute("doctors", doctorInfoService.getAllDoctors());
//        return "/doctors";
//    }

}
