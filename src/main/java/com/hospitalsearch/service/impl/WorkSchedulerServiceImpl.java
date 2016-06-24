package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dao.WorkSchedulerDAO;
import com.hospitalsearch.service.WorkSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by igortsapyak on 29.05.16.
 */
@Service
@Transactional
public class WorkSchedulerServiceImpl implements WorkSchedulerService {

    @Autowired
    private WorkSchedulerDAO workSchedulerDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public String getWorkScheduler(Long doctorId) {
        return workSchedulerDAO.getWorkScheduler(doctorId);
    }

    @Override
    public void updateWorkScheduler(String doctorId, String workScheduler) {
        workSchedulerDAO.updateWorkScheduler(Long.parseLong(doctorId), workScheduler);
    }

    @Override
    public String getWorkSchedulerByDoctor(String doctor){
        return workSchedulerDAO.getWorkScheduler(userDAO.getByEmail(doctor)
                .getUserDetails().getDoctorsDetails().getId());
    }

}
