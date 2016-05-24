package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.PatientDAO;
import com.hospitalsearch.entity.PatientInfo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by deplague on 5/11/16.
 */
@Repository
public class PatientDAOImpl extends GenericDAOImpl<PatientInfo,Long> implements PatientDAO {
    @Autowired
    public PatientDAOImpl(SessionFactory factory) {
        this.setSessionFactory(factory);
    }
}
