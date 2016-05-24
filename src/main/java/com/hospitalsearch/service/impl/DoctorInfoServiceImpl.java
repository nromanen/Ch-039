package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.DoctorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deplague on 5/10/16.
 */
@Service
public class DoctorInfoServiceImpl implements DoctorInfoService {
    @Autowired
    private DoctorInfoDAO dao;
    @Override
    public void save(DoctorInfo newDoctor) {
        dao.save(newDoctor);
    }

    @Override
    public void delete(DoctorInfo doctor) {
        dao.delete(doctor);
    }

    @Override
    public void update(DoctorInfo updatedDoctor) {
        dao.update(updatedDoctor);
    }

    @Override
    public DoctorInfo getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<DoctorInfo> getAll() {
        return dao.getAll();
    }

    @Override
    public List<UserDetail> findByDepartmentId(Long id) {
        return dao.findByDepartmentId(id);
    }

}
