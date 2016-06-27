package com.hospitalsearch.service;

import com.hospitalsearch.entity.WorkScheduler;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by igortsapyak on 29.05.16.
 */

@Transactional
public interface WorkSchedulerService {

    WorkScheduler getByDoctorId(Long doctorId);

    void saveWorkScheduler(String workScheduler, Long doctorId);

    String getByDoctorEmail(String doctorEmail);

}
