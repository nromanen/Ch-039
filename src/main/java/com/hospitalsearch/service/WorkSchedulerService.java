package com.hospitalsearch.service;

/**
 * Created by igortsapyak on 29.05.16.
 */
public interface WorkSchedulerService {

    String getWorkScheduler(Long doctorId);

    void updateWorkScheduler(String doctorIdAndAppointmentDuration, String workScheduler);

    String getWorkSchedulerByDoctor(String doctor);

}
