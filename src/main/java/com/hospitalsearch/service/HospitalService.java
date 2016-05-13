package com.hospitalsearch.service;

import com.hospitalsearch.entity.Hospital;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Component
@Transactional
public interface HospitalService {
    void save(Hospital newHospital);
    void delete(Hospital hospital);
    void update(Hospital updatedHospital);
    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    Hospital getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<Hospital> getAll();
}