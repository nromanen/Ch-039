package com.hospitalsearch.service;

import com.hospitalsearch.entity.WorkInterval;

import java.util.List;
import java.util.Map;

/**
 * Created by igortsapyak on 04.05.16.
 */
public interface WorkIntervalService {

    List<WorkInterval> getAllbyDoctorId(Long doctorId);

    void saveWorkInterval(WorkInterval workInterval);

    void updateWorkinterval(WorkInterval workInterval);

    void deleteInterval(WorkInterval workInterval);

    void actionControl(Map<String, String[]> workInterval, Long doctorId);


}
