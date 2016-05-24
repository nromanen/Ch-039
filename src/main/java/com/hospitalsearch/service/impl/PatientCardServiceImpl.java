package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.PatientCardDAO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.service.PatientCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PatientCardServiceImpl implements PatientCardService {

    @Autowired
    private PatientCardDAO patientCardDAO;


    @Override
    public PatientCard add(PatientCard patientCard) {
        PatientCard patientCardFromDB = patientCardDAO.add(patientCard);
        return patientCardFromDB;
    }

    @Override
    public void save(PatientCard patientCard) {
        patientCardDAO.save(patientCard);
    }

    @Override
    public void remove(PatientCard patientCard) {
        patientCardDAO.delete(patientCard);
    }

    @Override
    public PatientCard getById(Long id) {
        return patientCardDAO.getById(id);
    }
}
