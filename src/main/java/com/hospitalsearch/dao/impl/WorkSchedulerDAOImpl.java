package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.WorkSchedulerDAO;
import com.hospitalsearch.entity.Appointment;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * Created by igortsapyak on 29.05.16.
 */
@Repository
public class WorkSchedulerDAOImpl extends GenericDAOImpl<Appointment, Long> implements WorkSchedulerDAO {


    private static final String UPDATE_WORK_SCHEDULER = "UPDATE workscheduler SET workscheduler =? WHERE doctor_id =?";

    private static final String CREATE_WORK_SCHEDULER = "INSERT into workscheduler VALUES (?,?)";

    private static final String GET_WORK_SCHEDULER = "SELECT * from workscheduler WHERE doctor_id = ?";

    @Autowired
    public WorkSchedulerDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    public String getWorkScheduler(Long doctorId) {

        Session session = getSessionFactory().getCurrentSession();
        return ((Object[]) session
                .createSQLQuery(GET_WORK_SCHEDULER)
                .setParameter(0, doctorId)
                .uniqueResult())[1]
                .toString();
    }

    @Override
    public void updateWorkScheduler(Long doctorId, String scheduler) {

        Session session = getSessionFactory().getCurrentSession();

        if (session.createSQLQuery(UPDATE_WORK_SCHEDULER)
                .setParameter(0, scheduler)
                .setParameter(1, doctorId).executeUpdate() == 0) {
            session.createSQLQuery(CREATE_WORK_SCHEDULER)
                    .setParameter(0, doctorId)
                    .setParameter(1, scheduler)
                    .executeUpdate();

        }

    }


}
