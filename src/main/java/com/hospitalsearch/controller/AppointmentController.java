package com.hospitalsearch.controller;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dao.UserDetailDAO;
import com.hospitalsearch.dao.impl.DoctorInfoDAOImpl;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.User;
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
    private UserDetailService userDetailService;
    @Autowired
    private FeedbackService feedbackService;

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
        return "manage";
    }

    @RequestMapping(value = "/**/supplyAppointment", method = RequestMethod.POST)
    String eventProcessor(HttpServletRequest request,
                          @RequestParam("id")Long id, @RequestParam("principal") String principal) {
        appointmentService.actionControl(request.getParameterMap(), id, principal);
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping(value = "/**/getAppointmentsByPatient",method = RequestMethod.GET)
    public List<Appointment> patientsAppointments(@RequestParam("patient")String patient) {
        return appointmentService.getGetAllByPatient(patient);
    }

    @ResponseBody
    @RequestMapping(value = "/getAppointmentsByDoctor",method = RequestMethod.GET)
    public List<Appointment> doctorsAppointments(@RequestParam("doctor")String doctor) {
        return appointmentService.getAllByDoctor(doctor);
    }


    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    String getAppointments() {
        return "appointments";
    }

    @RequestMapping(value = "/doctorInfo", method = RequestMethod.GET)
    String getDoctors(@RequestParam("doctor") String docotor) {
        System.out.println(docotor);
        return "/hospital/{*}/department/1/doctor/1/dashboard";
    }


}
