package com.hospitalsearch.service;

import com.hospitalsearch.entity.Appointment;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by igortsapyak on 04.05.16.
 */

@Transactional
public interface AppointmentService {

    List<Appointment> getAllbyDoctorId(Long doctorId);

    void saveAppointment(Appointment appointment);

    void updateAppointment(Appointment appointment);

    void deleteInterval(Appointment appointment);

    void actionControl(Map<String, String[]> Appointment, Long doctorId, String principal);
    @Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
    List<Appointment> getGetAllByPatient(String patient);
    @Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
    List<Appointment> getAllByDoctor(String doctor);
    
}
