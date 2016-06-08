package com.hospitalsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.dao.PatientCardDAO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.service.PatientCardService;


@Service
@Transactional
public class PatientCardServiceImpl implements PatientCardService {

    @Autowired
    private PatientCardDAO dao;


    @Override
    public PatientCard add(PatientCard patientCard) {
        patientCard = dao.add(patientCard);

        return patientCard;
    }

    @Override
    public void save(PatientCard patientCard) {
        dao.save(patientCard);
    }

    @Override
    public void remove(PatientCard patientCard) {
        dao.delete(patientCard);
    }

    @Override
    public PatientCard getById(Long id) {
        return dao.getById(id);
    }
}
