package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.UserDetailDAO;
import com.hospitalsearch.entity.UserDetail;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by deplague on 5/11/16.
 */
@Repository
public class UserDetailDAOImpl extends GenericDAOImpl<UserDetail,Long> implements UserDetailDAO{
    @Autowired
    public UserDetailDAOImpl(SessionFactory factory){
        this.setSessionFactory(factory);
    }

}
