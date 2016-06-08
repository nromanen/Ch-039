package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.PatientCardDAO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.service.PatientCardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PatientCardServiceImpl implements PatientCardService {

    final static Logger log = Logger.getLogger(PatientCardService.class);

    @Autowired
    private PatientCardDAO dao;


    @Override
    public PatientCard add(PatientCard patientCard) {
        try{
            patientCard = dao.add(patientCard);
            log.info("Save patient card" + patientCard);
        }catch (Exception e){
            log.error("Saving patient card" + patientCard);
        }
        return patientCard;
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
