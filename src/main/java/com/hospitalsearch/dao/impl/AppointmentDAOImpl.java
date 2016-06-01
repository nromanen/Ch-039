package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.AppointmentDAO;
import com.hospitalsearch.entity.Appointment;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


@Repository
public class AppointmentDAOImpl extends GenericDAOImpl<Appointment, Long> implements AppointmentDAO {
    @Resource
    private Environment environment;

    @Autowired
    public AppointmentDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Appointment> getAllbyDoctorId(Long doctorId) {

        List<Appointment> appointments = getSessionFactory().openSession().createCriteria(Appointment.class)
                .add(Restrictions.eq("doctorInfo.id", doctorId)).list();
        return appointments;
    }

//    public String getWorkScheduler(Long doctorId){
//        Object[] objects =(Object[]) getSessionFactory().openSession().createSQLQuery("SELECT * from workscheduler WHERE doctor_id =:id").setParameter("id",doctorId).uniqueResult();
//        String string = objects[1].toString();
//        return string;
//    }
//
//    public void updateWorkScheduler(Long doctorId, String scheduler){
//        getSessionFactory().openSession().createSQLQuery("UPDATE workscheduler SET workscheduler =:scheduler WHERE doctor_id =:id").setParameter("scheduler",scheduler).setParameter("id",doctorId).executeUpdate();
//    }


}
