package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;
/**
 * @author Andrew Jasinskiy
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {

    private final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Autowired
    public UserDAOImpl(SessionFactory factory) {
        this.setSessionFactory(factory);
    }

    @Override
    public User getByEmail(String email) {
        try {
            logger.info("getUserByEmail email: " + email);
            Criteria criteria = this.currentSession().createCriteria(User.class);
            criteria.add(Restrictions.eq("email", email));
            return (User) criteria.uniqueResult();
        } catch (Exception e) {
            logger.error("Error get user by email: " + email + e);
            throw e;
        }
    }

    @Override
    public void changeStatus(long id) {
        User user = super.getById(id);
        logger.info("Change " + user.getEmail() + " status to " + !user.getEnabled());
        user.setEnabled(!user.getEnabled());
    }

    @Override
    public List<User> getByRole(long id) {
       /* Query query = this.currentSession().getNamedQuery("SELECT_BY_ROLE").setParameter("id", id);
        return query.list();*/
        return null;
    }

    @Override
    public Boolean emailExists(String email) {
        return nonNull(getByEmail(email));
    }


    @Override
    public List<User> getAllEnabledUsers() {
        Query query =  this.currentSession().getNamedQuery("SELECT_ALL_ENABLED_USERS");
        List <User> users = query.list();
        Collections.sort(users);
        return users;
    }

    @Override
    public List<User> getAllDisabledUsers() {
        Query query =  this.currentSession().getNamedQuery("SELECT_ALL_DISABLED_USERS");
        List <User> users = query.list();
        Collections.sort(users);
        return users;
    }

    @Override
    public List<User> getAll() {
        List <User> users = super.getAll();
        Collections.sort(users);
        return users ;
    }
}



