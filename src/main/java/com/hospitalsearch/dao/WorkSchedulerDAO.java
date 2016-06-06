package com.hospitalsearch.dao;

import org.springframework.stereotype.Component;

/**
 * Created by igortsapyak on 29.05.16.
 */

@Component
public interface WorkSchedulerDAO {

    String getWorkScheduler(Long doctorId);

    void updateWorkScheduler(Long doctorId, String scheduler);

    }
