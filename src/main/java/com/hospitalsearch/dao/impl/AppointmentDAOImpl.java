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
        List<Appointment> appointments = getSessionFactory().getCurrentSession().createCriteria(Appointment.class)
                .add(Restrictions.eq("doctorInfo.id", doctorId)).list();
        return appointments;
    }

    @Override
    public List<Appointment> getAllByPatient(Long userDetailId) {
        List<Appointment> appointments = getSessionFactory().getCurrentSession().createCriteria(Appointment.class)
                .add(Restrictions.eq("userDetail.id", userDetailId))
                .list();
        return appointments;
    }

    @Override
    public List<Appointment> getAllByDoctor(Long doctorId) {
        List<Appointment> appointments = getSessionFactory().getCurrentSession().createCriteria(Appointment.class)
                .add(Restrictions.eq("doctorInfo.id", doctorId))
                .list();
        return appointments;
    }

}
