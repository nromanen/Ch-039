package com.hospitalsearch.dao;

import com.hospitalsearch.entity.Appointment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by igortsapyak on 04.05.16.
 */
@Component
public interface AppointmentDAO extends GenericDAO<Appointment, Long> {

     List<Appointment> getAllbyDoctorId(Long doctorId);


}
