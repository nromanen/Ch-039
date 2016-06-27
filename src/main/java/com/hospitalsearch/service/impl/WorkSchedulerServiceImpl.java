package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dao.WorkSchedulerDAO;
import com.hospitalsearch.entity.WorkScheduler;
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
    private DoctorInfoDAO doctorInfoDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public WorkScheduler getByDoctorId(Long doctorId) {
        return workSchedulerDAO.getByDoctorId(doctorId);
    }

    @Override
    public void saveWorkScheduler(String workSchedulerString, Long doctorId) {
        WorkScheduler workScheduler;
        workScheduler = workSchedulerDAO.getByDoctorId(doctorId);
        if (workScheduler==null) {
            workScheduler = new WorkScheduler();
        }
        workScheduler.setDoctorInfo(doctorInfoDAO.getById(doctorId));
        workScheduler.setWorkScheduler(workSchedulerString);
        workSchedulerDAO.save(workScheduler);
    }

    @Override
    public String getByDoctorEmail(String doctorEmail) {
        return workSchedulerDAO.getByDoctorId(userDAO.getByEmail(doctorEmail)
                .getUserDetails().getDoctorsDetails().getId()).getWorkScheduler();
    }

}
