package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.AppointmentDAO;
import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dao.UserDetailDAO;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.AppointmentService;
import com.hospitalsearch.util.AppointmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by igortsapyak on 04.05.16.
 */
@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    UserDAO userDAO;
    @Autowired
    UserDetailDAO userDetailDAO;
    @Autowired
    private AppointmentDAO appointmentDao;
    @Autowired
    private DoctorInfoDAO doctorInfoDAO;

    @Override
    public void actionControl(Map<String, String[]> AppointmentParams, Long doctorId, String principal) {

        UserDetail userDetail = userDetailDAO.getById(userDAO.getByEmail(principal).getId());

        DoctorInfo doctorInfo = doctorInfoDAO.getById(doctorId);

        AppointmentDto AppointmentDto = AppointmentDtoService.createAppointmentDto(AppointmentParams, doctorInfo);

        Appointment appointment = AppointmentDto.getAppointment();

        appointment.setDoctorInfo(doctorInfo);

        appointment.setUserDetail(userDetail);

        switch (AppointmentDto.getStatus()) {
            case "inserted":
                saveAppointment(appointment);

                break;
            case "deleted":
                deleteInterval(appointment);

                break;
            case "updated":
                updateAppointment(appointment);

                break;
            default:

        }

    }

    @Override
    public List<Appointment> getGetAllByPatient(String patient) {
        List<Appointment> appointments = appointmentDao.getAllByPatient(userDAO.getByEmail(patient).getId());

        for (Appointment appointment : appointments) {
            appointment.setText(appointment.getDoctorInfo().getUserDetails().getFirstName()
                    + " " + appointment.getDoctorInfo().getUserDetails().getLastName());
        }

        return appointments;
    }


    @Override
    public List<Appointment> getAllByDoctor(String doctor) {
        List<Appointment> appointments = appointmentDao.getAllbyDoctorId(userDAO.getByEmail(doctor).getUserDetails()
                .getDoctorsDetails().getId());

        for (Appointment appointment : appointments) {
            appointment.setText(appointment.getUserDetail().getFirstName() + " " + appointment.getUserDetail().getLastName());
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
