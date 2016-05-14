package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.WorkIntervalDAO;
import com.hospitalsearch.entity.WorkInterval;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class WorkIntervalDAOImpl extends GenericDAOImpl<WorkInterval, Long> implements WorkIntervalDAO {

    @Autowired
    public WorkIntervalDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<WorkInterval> getAllbyDoctorId(Long doctorId) {
        List<WorkInterval> workIntervals = getSessionFactory().openSession().createCriteria(WorkInterval.class)
                .add(Restrictions.eq("doctorInfo.id", doctorId)).list();
        return workIntervals;
    }

}
