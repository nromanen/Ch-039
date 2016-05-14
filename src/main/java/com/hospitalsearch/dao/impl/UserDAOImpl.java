package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by deplague on 5/11/16.
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User,Long> implements UserDAO{
    @Autowired
    public UserDAOImpl(SessionFactory factory){this.setSessionFactory(factory);}
}
