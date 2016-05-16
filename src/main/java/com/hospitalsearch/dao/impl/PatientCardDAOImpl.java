package com.hospitalsearch.dao.impl;


import com.hospitalsearch.dao.PatientCardDAO;
import com.hospitalsearch.entity.PatientCard;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository ("patientCardDAO")
public class PatientCardDAOImpl extends GenericDAOImpl<PatientCard, Long> implements PatientCardDAO {

    @Autowired
    public PatientCardDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }


    @Override
    public PatientCard add(PatientCard patientCard) {
        PatientCard patientCardFromDB = (PatientCard) this.getSessionFactory().getCurrentSession().merge(patientCard);
        return patientCardFromDB;
    }
}
