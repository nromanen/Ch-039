package com.hospitalsearch.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by igortsapyak on 29.05.16.
 */

@Transactional
public interface WorkSchedulerService {

    String getWorkScheduler(Long doctorId);

    void updateWorkScheduler(String doctorIdAndAppointmentDuration, String workScheduler);

    String getWorkSchedulerByDoctor(String doctor);

}
