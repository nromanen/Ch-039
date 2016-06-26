package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.WorkSchedulerDAO;
import com.hospitalsearch.entity.WorkScheduler;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by igortsapyak on 29.05.16.
 */
@Repository
public class WorkSchedulerDAOImpl extends GenericDAOImpl<WorkScheduler, Long> implements WorkSchedulerDAO {

    @Autowired
    public WorkSchedulerDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    public WorkScheduler getByDoctorId(Long doctorId){
        Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(WorkScheduler.class);
        criteria.add(Restrictions.eq("doctorInfo.id",doctorId));
        return (WorkScheduler) criteria.uniqueResult();
    }

}
