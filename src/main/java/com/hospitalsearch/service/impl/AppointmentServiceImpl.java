package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.AppointmentDAO;
import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.DoctorInfo;
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
    private AppointmentDAO AppointmentDao;

    @Autowired
    private DoctorInfoDAO doctorInfoDAO;

    @Override
    public void actionControl(Map<String, String[]> AppointmentParams, Long doctorId){

        DoctorInfo doctorInfo = doctorInfoDAO.getById(doctorId);

        AppointmentDto AppointmentDto = AppointmentDtoService.createAppointmentDto(AppointmentParams, doctorInfo);

        Appointment appointment = AppointmentDto.getAppointment();

        appointment.setDoctorInfo(doctorInfo);

        switch (AppointmentDto.getStatus()){
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
    public List<Appointment> getAllbyDoctorId(Long doctorId) {
        return AppointmentDao.getAllbyDoctorId(doctorId);
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        AppointmentDao.save(appointment);
    }

    @Override
    public void deleteInterval(Appointment appointment) {
        AppointmentDao.delete(appointment);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        AppointmentDao.update(appointment);
    }
}
