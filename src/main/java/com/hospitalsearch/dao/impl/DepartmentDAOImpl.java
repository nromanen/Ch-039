package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.DepartmentDAO;
import com.hospitalsearch.entity.Department;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Repository
public class DepartmentDAOImpl extends GenericDAOImpl<Department,Long> implements DepartmentDAO{
    @Autowired(required = true)
    public DepartmentDAOImpl(SessionFactory factory){
        this.setSessionFactory(factory);
    }

    @Override
    public List<Department> findByHospitalId(Long id) {
        return ((List<Department>) getHibernateTemplate()
                .findByCriteria(DetachedCriteria.forClass(Department.class).add(Restrictions.eq("hospital.id",id))));
    }
}
