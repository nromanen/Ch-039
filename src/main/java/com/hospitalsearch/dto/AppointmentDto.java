package com.hospitalsearch.dto;


import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.DoctorInfo;

/**
 * Created by igortsapyak on 10.05.16.
 */
public class AppointmentDto {

    private String status;

    private Appointment appointment;

    private DoctorInfo doctorInfo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public DoctorInfo getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
    }
}
