package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalDAO dao;

    @Override
    public void save(Hospital newHospital) {
        dao.save(newHospital);
    }

    @Override
    public void delete(Hospital hospital) {
        dao.delete(hospital);
    }

    @Override
    public void update(Hospital updatedHospital) {
        dao.update(updatedHospital);
    }

    @Override
    public Hospital getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<Hospital> getAll() {
        return dao.getAll();
    }
}
