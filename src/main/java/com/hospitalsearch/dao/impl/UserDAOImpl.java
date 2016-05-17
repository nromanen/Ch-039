package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Objects.nonNull;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User,Long> implements UserDAO{
    @Autowired
    public UserDAOImpl(SessionFactory factory){this.setSessionFactory(factory);}

    @Override
    public User getById(Long id) {
    	return this.currentSession().get(User.class, id);
    }
    
    @Override
    public User getByEmail(String email) {
        Criteria criteria = this.currentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        return (User) criteria.uniqueResult();
    }

    @Override
    public void changeStatus(User user) {
        if(user.getEnabled()) {
            user.setEnabled(false);
        }else{
            user.setEnabled(true);
        }
        update(user);
    }

    @Override
    public List<User> getByRole(long id) {
        Query query = this.currentSession().getNamedQuery(User.SELECT_BY_ROLE).setParameter("id",id);
        return query.list();
    }

    @Override
    public Boolean emailExists(String email) {
        return nonNull(getByEmail(email));
    }
}
