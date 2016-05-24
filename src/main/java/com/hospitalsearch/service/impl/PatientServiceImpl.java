package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.PatientDAO;
import com.hospitalsearch.entity.PatientInfo;
import com.hospitalsearch.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Service
public class PatientServiceImpl implements PatientService {
    @Autowired(required = true)
    private PatientDAO dao;
    @Override
    public void save(PatientInfo newPatient) {
        dao.save(newPatient);
    }

    @Override
    public void delete(PatientInfo patient) {
        dao.delete(patient);
    }

    @Override
    public void update(PatientInfo updatedPatient) {
        dao.update(updatedPatient);
    }

    @Override
    public PatientInfo getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<PatientInfo> getAll() {
        return dao.getAll();
    }
}
