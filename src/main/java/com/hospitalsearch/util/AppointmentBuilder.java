package com.hospitalsearch.util;

import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.DoctorInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by igortsapyak on 09.05.16.
 */
public class AppointmentBuilder {

    private Appointment appointmentInstance = new Appointment();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public AppointmentBuilder setStart_date(String start_date){
        appointmentInstance.setStart_date(LocalDateTime.parse(start_date, formatter));
        return this;
    }

    public AppointmentBuilder setEnd_date(String end_date){
        appointmentInstance.setEnd_date(LocalDateTime.parse(end_date, formatter));
        return this;
    }

    public AppointmentBuilder setText(String text){
        appointmentInstance.setText(text);
        return this;
    }

    public AppointmentBuilder setId(Long id){
        appointmentInstance.setId(id);
        return this;
    }

    public AppointmentBuilder setDoctorInfo(DoctorInfo doctorInfo){
        appointmentInstance.setDoctorInfo(doctorInfo);
        return this;
    }

    public AppointmentBuilder setAppointmentReason(String appointmentReason){
        appointmentInstance.setAppointmentReason(appointmentReason);
        return this;
    }


    public Appointment build(){
        return appointmentInstance;
    }

}
