package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.entity.Hospital;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by deplague on 5/11/16.
 */
@Repository
public class HospitalDAOImpl extends  GenericDAOImpl<Hospital,Long> implements HospitalDAO{

        @Autowired
        public HospitalDAOImpl(SessionFactory sessionFactory){
            this.setSessionFactory(sessionFactory);
        }
}
