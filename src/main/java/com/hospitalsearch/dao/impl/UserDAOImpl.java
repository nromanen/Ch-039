package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by deplague on 5/11/16.
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User,Long> implements UserDAO{
    @Autowired
    public UserDAOImpl(SessionFactory factory){this.setSessionFactory(factory);}

    @Override
    public User getByEmail(String email) {
        Criteria criteria = this.currentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        return (User) criteria.uniqueResult();
    }

    @Override
    public void changeStatus(User user) {
        user.setEnabled(false);
        update(user);
    }
}
