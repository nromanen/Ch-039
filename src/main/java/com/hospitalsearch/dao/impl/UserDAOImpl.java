package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dto.UserSearchDTO;
import com.hospitalsearch.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
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
            //fixed by Igor
//            Criteria criteria = this.currentSession().createCriteria(User.class);
            Criteria criteria = getSessionFactory().openSession().createCriteria(User.class);
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


    //Illia

    @Override
    public List<User> getByRole(String role) {
        Criteria criteria = this.currentSession().createCriteria(User.class, "user")
                .createAlias("user.userRoles", "userRoles").add(Restrictions.eq("userRoles.type", role))
                .createAlias("user.userDetails", "details")
                .add(Restrictions.isNotNull("details.patientCard"))
                .add(Restrictions.isNotNull("details.firstName"))
                .add(Restrictions.isNotNull("details.lastName"))
                .addOrder(Order.asc("details.firstName"));
        return criteria.list();
    }

    @Override
    public List<User> searchByRole(String role, String search) {
        Criteria criteria = this.currentSession().createCriteria(User.class, "user").add(Restrictions.isNotNull("user.userDetails"))
                .createAlias("user.userRoles", "userRoles")
                .add(Restrictions.eq("userRoles.type", role))
                .createAlias("user.userDetails", "details").add(Restrictions.or(
                                Restrictions.like("user.email", search, MatchMode.ANYWHERE).ignoreCase(),
                                Restrictions.like("details.firstName", search, MatchMode.ANYWHERE).ignoreCase(),
                                Restrictions.like("details.lastName", search, MatchMode.ANYWHERE).ignoreCase()
                        )
                ).addOrder(Order.asc("details.firstName"));
        return criteria.list();
    }

    //Illia

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

    @SuppressWarnings("unchecked")
    @Override
    public List<User> searchUser(UserSearchDTO userSearch) {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(User.class);

        criteria.createAlias("userRoles", "roles");
        criteria.createAlias("userDetails", "detail");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.like("roles.type", userSearch.getRole(), MatchMode.ANYWHERE));

        if(userSearch.getStatus().equals("All")){
            criteria.add(Restrictions.conjunction());
        }else{
            criteria.add(Restrictions.eq("enabled", Boolean.parseBoolean(userSearch.getStatus())));
        }

        if(userSearch.getAllField()!=null) {
            criteria.add(searchInAllFields(userSearch.getAllField()));
            return criteria.list();
        }

        criteria.add(Restrictions.or(Restrictions.like("email", userSearch.getEmail(), MatchMode.ANYWHERE),
                        Restrictions.like("detail.firstName", userSearch.getFirstName(), MatchMode.ANYWHERE),
                        Restrictions.like("detail.lastName", userSearch.getLastName(), MatchMode.ANYWHERE)));
        return criteria.list();
    }

    private Disjunction searchInAllFields(String value){
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.ilike("email", value, MatchMode.ANYWHERE));
        disjunction.add(Restrictions.ilike("detail.firstName", value, MatchMode.ANYWHERE));
        disjunction.add(Restrictions.ilike("detail.lastName", value, MatchMode.ANYWHERE));
        return disjunction;
    }
}



