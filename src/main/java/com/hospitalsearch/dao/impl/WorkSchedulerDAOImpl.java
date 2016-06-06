package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.WorkSchedulerDAO;
import com.hospitalsearch.entity.Appointment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by igortsapyak on 29.05.16.
 */
@Repository

public class WorkSchedulerDAOImpl extends GenericDAOImpl<Appointment, Long> implements WorkSchedulerDAO {

    @Autowired
    public WorkSchedulerDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    public String getWorkScheduler(Long doctorId) {

        return ((Object[]) getSessionFactory()
                .openSession()
                .createSQLQuery("SELECT * from workscheduler WHERE doctor_id =:id")
                .setParameter("id", doctorId)
                .uniqueResult())[1]
                .toString();
    }

    @Override
    public void updateWorkScheduler(Long doctorId, String scheduler) {
        getSessionFactory().openSession().createSQLQuery("UPDATE workscheduler SET workscheduler =:scheduler WHERE doctor_id =:id")
                .setParameter("scheduler",scheduler)
                .setParameter("id",doctorId).executeUpdate();


    }
}
