package com.hospitalsearch.dao;

import com.hospitalsearch.entity.WorkInterval;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by igortsapyak on 04.05.16.
 */
@Component
public interface WorkIntervalDAO extends GenericDAO<WorkInterval, Long> {

     List<WorkInterval> getAllbyDoctorId(Long doctorId);


}
