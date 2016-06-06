package com.hospitalsearch.service;

import com.hospitalsearch.entity.Appointment;

import java.util.List;
import java.util.Map;

/**
 * Created by igortsapyak on 04.05.16.
 */
public interface AppointmentService {

    List<Appointment> getAllbyDoctorId(Long doctorId);

    void saveAppointment(Appointment appointment);

    void updateAppointment(Appointment appointment);

    void deleteInterval(Appointment appointment);

    void actionControl(Map<String, String[]> Appointment, Long doctorId);
    
}
