package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.AppointmentDAO;
import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dao.UserDetailDAO;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.AppointmentService;
import com.hospitalsearch.dto.AppointmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by igortsapyak on 04.05.16.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserDetailDAO userDetailDAO;
    @Autowired
    private AppointmentDAO appointmentDao;
    @Autowired
    private DoctorInfoDAO doctorInfoDAO;

    @Override
    public void actionControl(Map<String, String[]> appointmentParams, Long doctorId, String principal) {
        UserDetail userDetail = userDetailDAO.getById(userDAO.getByEmail(principal).getId());
        DoctorInfo doctorInfo = doctorInfoDAO.getById(doctorId);
        AppointmentDto appointmentDto = AppointmentDtoService.createAppointmentDto(appointmentParams, doctorInfo);
        Appointment appointment = appointmentDto.getAppointment();
        appointment.setDoctorInfo(doctorInfo);
        appointment.setUserDetail(userDetail);
        switch (appointmentDto.getStatus()) {
            case "inserted":
                saveAppointment(appointment);
                break;
            case "deleted":
                deleteInterval(appointment);
                break;
            case "updated":
                updateAppointment(appointment);
                break;
        }
    }

    @Override
    public List<Appointment> getAllByPatientEmail(String patientEmail) {
        List<Appointment> appointments = appointmentDao.getAllByPatient(userDAO.getByEmail(patientEmail).getId());
        for (Appointment appointment : appointments) {
            appointment.setText(appointment.getDoctorInfo().getUserDetails().getFirstName()
                    + " " + appointment.getDoctorInfo().getUserDetails().getLastName());
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAllByDoctorEmail(String doctorEmail) {
        List<Appointment> appointments = appointmentDao.getAllbyDoctorId(userDAO.getByEmail(doctorEmail).getUserDetails()
                .getDoctorsDetails().getId());
        for (Appointment appointment : appointments) {
            appointment.setText(appointment.getUserDetail().getFirstName() + " " + appointment.getUserDetail().getLastName()
                    +" - "+appointment.getText());
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAllbyDoctorId(Long doctorId) {
        return appointmentDao.getAllbyDoctorId(doctorId);
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        appointmentDao.save(appointment);
    }

    @Override
    public void deleteInterval(Appointment appointment) {
        appointmentDao.delete(appointment);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        appointmentDao.update(appointment);
    }
}
