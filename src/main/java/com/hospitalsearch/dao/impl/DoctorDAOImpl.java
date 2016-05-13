package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.DoctorDAO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.UserDetail;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorDAOImpl extends GenericDAOImpl<DoctorInfo,Long> implements DoctorDAO {

    @Autowired
    public DoctorDAOImpl(SessionFactory factory) {
        this.setSessionFactory(factory);
    }

    @Override
    public List<UserDetail> findByDepartmentId(Long id) {
        return (List<UserDetail>) getHibernateTemplate()
        		.findByNamedParam("select u from UserDetail u join u.doctorsDetails d join d.departments dep where dep.id = :id ","id",id);
    }
}
