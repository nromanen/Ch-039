package com.hospitalsearch.service;

import com.hospitalsearch.entity.PatientInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Transactional
public interface PatientService {
    void save(PatientInfo newPatient);
    void delete(PatientInfo patient);
    void update(PatientInfo updatedPatient);
    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    PatientInfo getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<PatientInfo> getAll();
}

