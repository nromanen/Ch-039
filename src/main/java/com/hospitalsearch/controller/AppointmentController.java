package com.hospitalsearch.controller;

import com.hospitalsearch.dao.AppointmentDAO;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.AppointmentService;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalsearch.service.FeedbackService;
import com.hospitalsearch.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private DepartmentService departmentService;

    @ResponseBody
    @RequestMapping(value = "/**/getAppointments", method = RequestMethod.GET)
    public List<Appointment> listAllAppointments(@RequestParam("id") String id) {
        return appointmentService.getAllbyDoctorId(Long.parseLong(id));
    }

    @RequestMapping(value = "/hospital/{*}/department/{dep_id}/doctor/{d_id}/dashboard", method = RequestMethod.GET)
    public String getDashboard(
            @PathVariable("dep_id") Long departmentId,
            @PathVariable("d_id") Long doctorId,
            ModelMap model) {
        Department d = departmentService.getById(departmentId);
        model.put("department", d);
        model.put("hospital", d.getHospital());
        model.put("formatter", DateTimeFormatter.ofPattern("d MMM uuuu hh:mm"));
        model.put("feedbacks", feedbackService.getByDoctorId(doctorId));
        UserDetail userDetail = userDetailService.getById(doctorId);
        model.addAttribute("id", userDetail.getDoctorsDetails().getId());
        model.addAttribute("doctor", userDetailService.getById(doctorId));
        return "start";
    }

    @RequestMapping(value = "/**/supplyAppointment", method = RequestMethod.POST)
    public String eventProcessor(HttpServletRequest request,
                                 @RequestParam("id") Long id, @RequestParam("principal") String principal) {
        appointmentService.actionControl(request.getParameterMap(), id, principal);
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping(value = "/**/getAppointmentsByPatient", method = RequestMethod.GET)
    public List<Appointment> patientsAppointments(@RequestParam("patient") String patient) {
        return appointmentService.getAllByPatientEmail(patient);
    }

    @ResponseBody
    @RequestMapping(value = "/getAppointmentsByDoctor", method = RequestMethod.GET)
    public List<Appointment> doctorsAppointments(@RequestParam("doctor") String doctor) {
        return appointmentService.getAllByDoctorEmail(doctor);
    }

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public String getAppointments() {
        return "appointments";
    }


    @RequestMapping(value = "/appointmentId", method = RequestMethod.GET)
    public String getCardByapointmentId(@RequestParam("appointmentId") Long appointmentId) {
        return "redirect:/card/items?userId="+appointmentDAO.getById(appointmentId).getUserDetail().getId();
    }

    @RequestMapping(value = "/**/sendMassage", method = RequestMethod.POST)
    public String sendMassageToEmail(@RequestBody Map<String, String> massageData){
        System.out.println(massageData.entrySet().toString());
        return "redirect:/";

    }


}
