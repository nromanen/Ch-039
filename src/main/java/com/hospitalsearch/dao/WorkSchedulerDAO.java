package com.hospitalsearch.dao;

import com.hospitalsearch.entity.WorkScheduler;
import org.springframework.stereotype.Component;

/**
 * Created by igortsapyak on 29.05.16.
 */

@Component
public interface WorkSchedulerDAO extends GenericDAO<WorkScheduler, Long> {

    WorkScheduler getByDoctorId(Long doctorId);

}
