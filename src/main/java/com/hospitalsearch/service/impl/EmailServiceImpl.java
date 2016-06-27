package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.AppointmentDAO;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.service.EmailService;
import org.springframework.beans.factory.access.BootstrapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by tsapy on 27.06.2016.
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Override
    public void sendMassageFromUserToUser(Map<String, String> massageData) {
        String doctorEmail = userDAO.getById(appointmentDAO.getById(Long.parseLong(massageData.get("eventId"))).getDoctorInfo().getUserDetails().getId()).getEmail();
        String patientEmail = userDAO.getById(appointmentDAO.getById(Long.parseLong(massageData.get("eventId"))).getUserDetail().getId()).getEmail();
        String principalEmail = massageData.get("principal");
        if (doctorEmail.equals(principalEmail)){
            newMethod(patientEmail, massageData.get("principalMassage"));
        }else {
            newMethod(doctorEmail, massageData.get("principalMassage"));
        }
    }

    void newMethod(String email, String reason){
        System.out.println(email + " Will send here!");
        System.out.println("Because" + reason);

    }
}
