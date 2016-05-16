package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dao.WorkIntervalDAO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.entity.WorkInterval;
import com.hospitalsearch.service.WorkIntervalService;
import com.hospitalsearch.util.WorkIntervalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by igortsapyak on 04.05.16.
 */
@Service
@Transactional
public class WorkIntervalServiceImpl implements WorkIntervalService {

    @Autowired
    private WorkIntervalDAO workIntervalDao;

    @Autowired
    private DoctorInfoDAO doctorInfoDAO;

    @Override
    public void actionControl(Map<String, String[]> workIntervalParams, Long doctorId){

        DoctorInfo doctorInfo = doctorInfoDAO.getById(doctorId);

        WorkIntervalDto workIntervalDto = WorkIntervalDtoService.createWorkintervalDto(workIntervalParams, doctorInfo);

        WorkInterval workInterval = workIntervalDto.getWorkInterval();

        workInterval.setDoctorInfo(doctorInfo);

        switch (workIntervalDto.getStatus()){
            case "inserted":
                saveWorkInterval(workInterval);
                
                break;
            case "deleted":
                deleteInterval(workInterval);
               
                break;
            case "updated":
                updateWorkinterval(workInterval);
               
                break;
            default:
               
        }

    }

    @Override
    public List<WorkInterval> getAllbyDoctorId(Long doctorId) {
        return workIntervalDao.getAllbyDoctorId(doctorId);
    }

    @Override
    public void saveWorkInterval(WorkInterval workInterval) {
        workIntervalDao.save(workInterval);
    }

    @Override
    public void deleteInterval(WorkInterval workInterval) {
        workIntervalDao.delete(workInterval);
    }

    @Override
    public void updateWorkinterval(WorkInterval workInterval) {
        workIntervalDao.update(workInterval);
    }
}
